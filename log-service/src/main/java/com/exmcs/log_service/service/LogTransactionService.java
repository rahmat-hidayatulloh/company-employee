package com.exmcs.log_service.service;

import com.exmcs.log_service.common.base.BaseService;
import com.exmcs.log_service.model.entity.LogTransaction;
import com.exmcs.log_service.model.request.PostLogTransactionRequest;
import com.exmcs.log_service.model.response.EmptyResponse;
import com.exmcs.log_service.repository.LogTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogTransactionService implements BaseService<PostLogTransactionRequest, EmptyResponse> {

    private final LogTransactionRepository logTransactionRepository;

    public LogTransactionService(LogTransactionRepository logTransactionRepository) {
        this.logTransactionRepository = logTransactionRepository;
    }

    @Override
    public EmptyResponse execute(PostLogTransactionRequest input) {

        logTransactionRepository.save(LogTransaction.builder()
                        .csvFilename(input.getCsvFilename())
                        .totalRecord(input.getTotalRecord())
                        .failedIdNotes(input.getFailedIdNotes())
                        .totalRecordFailed(input.getTotalRecordFailed())
                        .totalRecordSuccess(input.getTotalRecordSuccess())
                        .uploadDate(new Date())
                        .build());

        return new EmptyResponse();
    }
}
