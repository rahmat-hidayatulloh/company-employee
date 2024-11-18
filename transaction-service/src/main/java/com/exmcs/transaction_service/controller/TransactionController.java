package com.exmcs.transaction_service.controller;

import com.exmcs.transaction_service.model.request.PostTransactionRequest;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import com.exmcs.transaction_service.service.PostTransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final PostTransactionService postTransactionService;

    public TransactionController(PostTransactionService postTransactionService) {
        this.postTransactionService = postTransactionService;
    }

    @PostMapping("/post_file_csv")
    public EmptyResponse saveTransactionByFileCSV(@RequestParam MultipartFile file) {

        return postTransactionService.execute(PostTransactionRequest.builder()
                .file(file)
                .build());

    }
}
