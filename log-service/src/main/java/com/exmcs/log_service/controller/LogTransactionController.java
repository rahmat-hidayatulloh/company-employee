package com.exmcs.log_service.controller;

import com.exmcs.log_service.model.request.PostLogTransactionRequest;
import com.exmcs.log_service.model.response.EmptyResponse;
import com.exmcs.log_service.service.LogTransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log-transaction")
public class LogTransactionController {

    private LogTransactionService logTransactionService;

    public LogTransactionController(LogTransactionService logTransactionService) {
        this.logTransactionService = logTransactionService;
    }

    @PostMapping("/save")
    public EmptyResponse postLogTransaction(@RequestBody PostLogTransactionRequest request){

        return logTransactionService.execute(request);

    }
}
