package com.exmcs.transaction_service.service;

import com.exmcs.transaction_service.adaptor.CompanyServiceAdaptor;
import com.exmcs.transaction_service.adaptor.LogTransactionServiceAdaptor;
import com.exmcs.transaction_service.common.base.BaseService;
import com.exmcs.transaction_service.model.entity.Fee;
import com.exmcs.transaction_service.model.entity.Transaction;
import com.exmcs.transaction_service.model.request.PostTransactionRequest;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import com.exmcs.transaction_service.model.response.SourceOfEmployee;
import com.exmcs.transaction_service.repository.FeeRepository;
import com.exmcs.transaction_service.repository.TransactionRepository;
import jakarta.ws.rs.NotFoundException;
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
                    transactions.add(parseTransaction(line));
                    successRecords++;
                }
                catch (NotFoundException ex) {
                    failedRecords++;
/*                    failedIds.append(ex.getEmployeeId()).append(", ");*/
                } catch (Exception ex) {
                    failedRecords++;
                    failedIds.append("Invalid data").append(", ");
                }
//                catch (EmployeeNotFoundException ex) {
//                    failedRecords++;
//                    failedIds.append(ex.getEmployeeId()).append(", ");
//                } catch (Exception ex) {
//                    failedRecords++;
//                    failedIds.append("Invalid data").append(", ");
//                }
            }
            transactionRepository.saveAll(transactions);
            calculateFees(transactions);
            saveLog(fileName, totalRecords, successRecords, failedRecords, failedIds.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new EmptyResponse();
    }

    private Transaction parseTransaction(String line){
        String[] data = line.split(";");
        Long employeeId = Long.parseLong(data[0]);
        BigDecimal amount = new BigDecimal(data[1]);
        LocalDate transactionDate = LocalDate.parse(data[2]);

        SourceOfEmployee sourceOfEmployee = companyServiceAdaptor.isEmployeeExist(employeeId.toString());

        return Transaction.builder()
                .employeeId(sourceOfEmployee.getEmployee().getEmployeeId())
                .amount(amount)
                .tglTransaksi(transactionDate)
                .build();
    }

    private void calculateFees(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {

            SourceOfEmployee sourceOfEmployee = companyServiceAdaptor.isEmployeeExist(transaction.getEmployeeId().toString());

            List<Long> hierarchyIdList = (sourceOfEmployee.getPathHierarchyIds() != null) ?
                    Arrays.stream(sourceOfEmployee.getPathHierarchyIds().split(" > "))
                            .map(Long::parseLong)
                            .collect(Collectors.toList()) :
                    Collections.emptyList();

            BigDecimal totalAmount = hierarchyIdList.stream()
                    .map(id -> transactionRepository.findTotalAmountByEmployeeId(id))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal fee = calculateFee(totalAmount, hierarchyIdList.size());
            feeRepository.save(Fee.builder()
                            .employeeId(transaction.getEmployeeId())
                            .amountFee(fee)
                            .tglFee(LocalDate.now())
                    .build());
        }
    }
    private BigDecimal calculateFee(BigDecimal totalAmount, int hierarchySize) {
        return totalAmount.multiply(BigDecimal.valueOf(1.0 / hierarchySize));
    }

    private void saveLog(String fileName, int totalRecords, int successRecords, int failedRecords, String failedIds) {
        //Code Save Log
/*        LogTransaction log = new LogTransaction(fileName, totalRecords, successRecords, failedRecords, failedIds, LocalDate.now());
        logRepository.save(log);*/
    }


}
