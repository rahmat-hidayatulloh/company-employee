package com.exmcs.transaction_service.service;

import com.exmcs.transaction_service.adaptor.CompanyServiceAdaptor;
import com.exmcs.transaction_service.adaptor.LogTransactionServiceAdaptor;
import com.exmcs.transaction_service.common.base.BaseService;
import com.exmcs.transaction_service.exception.BussinessException;
import com.exmcs.transaction_service.model.dto.SourceOfEmployee;
import com.exmcs.transaction_service.model.dto.SourceOfLogTransaction;
import com.exmcs.transaction_service.model.entity.Fee;
import com.exmcs.transaction_service.model.entity.Transaction;
import com.exmcs.transaction_service.model.request.PostTransactionRequest;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import com.exmcs.transaction_service.repository.FeeRepository;
import com.exmcs.transaction_service.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostTransactionService implements BaseService<PostTransactionRequest, EmptyResponse> {

    private final TransactionRepository transactionRepository;
    private final FeeRepository feeRepository;
    private final CompanyServiceAdaptor companyServiceAdaptor;
    private final LogTransactionServiceAdaptor logTransactionServiceAdaptor;

    public PostTransactionService(TransactionRepository transactionRepository,
                                  FeeRepository feeRepository,
                                  CompanyServiceAdaptor companyServiceAdaptor,
                                  LogTransactionServiceAdaptor logTransactionServiceAdaptor) {
        this.transactionRepository = transactionRepository;
        this.feeRepository = feeRepository;
        this.companyServiceAdaptor = companyServiceAdaptor;
        this.logTransactionServiceAdaptor = logTransactionServiceAdaptor;
    }


    @Override
    public EmptyResponse execute(PostTransactionRequest input) {

        List<Transaction> transactions = new ArrayList<>();
        String fileName = input.getFile().getOriginalFilename();
        int totalRecords = 0, successRecords = 0, failedRecords = 0;
        StringBuilder failedIds = new StringBuilder();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input.getFile().getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalRecords++;
                try {
                    Transaction transaction = parseTransaction(line);

                    for (Fee fee : transaction.getFees()) {
                        fee.setTransaction(transaction);
                    }

                    transactionRepository.save(transaction);

                    transactions.add(transaction);
                    successRecords++;
                }
                catch (BussinessException ex) {
                    failedRecords++;
                    failedIds.append(ex.getMessage()).append("|| ");
                } catch (Exception ex) {
                    failedRecords++;
                    failedIds.append("Invalid data").append(", ");
                }
            }
            return saveLog(fileName, totalRecords, successRecords, failedRecords, failedIds.toString()).getBody();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction parseTransaction(String line){
        String[] data = line.split(";");
        Long employeeId = Long.parseLong(data[0]);
        BigDecimal amount = new BigDecimal(data[1]);
        LocalDate transactionDate = LocalDate.parse(data[2]);

        SourceOfEmployee sourceOfEmployee = companyServiceAdaptor.getSourceOfEmployee(employeeId.toString());

        List<Long> hierarchyIdList = (sourceOfEmployee.getPathHierarchyIds() != null) ?
                Arrays.stream(sourceOfEmployee.getPathHierarchyIds().split(" > "))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        List<Fee> fees = new ArrayList<>();
        for (Long hierarchyId : hierarchyIdList) {

            BigDecimal totalAmount = hierarchyIdList.stream()
                    .map(id -> amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal fee = calculateFee(totalAmount, hierarchyIdList.size());

            fees.add(Fee.builder()
                    .employeeId(hierarchyId)
                    .amountFee(fee)
                    .tglFee(LocalDate.now())
                    .build());
        }

        return Transaction.builder()
                .employeeId(sourceOfEmployee.getEmployeeId())
                .amount(amount)
                .tglTransaksi(transactionDate)
                .fees(fees)
                .build();
    }

    private BigDecimal calculateFee(BigDecimal totalAmount, int hierarchySize) {
        return totalAmount.multiply(BigDecimal.valueOf(1.0 / hierarchySize));
    }

    private ResponseEntity<EmptyResponse> saveLog(String fileName, int totalRecords, int successRecords, int failedRecords, String failedIds) {

        return logTransactionServiceAdaptor.postLogTransaction(SourceOfLogTransaction.builder()
                .csvFilename(fileName)
                .totalRecord(totalRecords)
                .totalRecordSuccess(successRecords)
                .totalRecordFailed(failedRecords)
                .failedIdNotes(failedIds)
                .build()
        );
    }


}
