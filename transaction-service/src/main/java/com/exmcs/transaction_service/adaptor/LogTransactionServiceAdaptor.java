package com.exmcs.transaction_service.adaptor;

import com.exmcs.transaction_service.exception.BussinessException;
import com.exmcs.transaction_service.model.dto.SourceOfEmployee;
import com.exmcs.transaction_service.model.dto.SourceOfLogTransaction;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class LogTransactionServiceAdaptor {

    private static final Logger log = LoggerFactory.getLogger(LogTransactionServiceAdaptor.class);

    private RestTemplate exmcsRestTemplate;

    @Value("${integration.exmcs.log-transaction-service.saving-log.url}")
    String savingLogTransactionUrl;

    public LogTransactionServiceAdaptor(RestTemplate exmcsRestTemplate) {
        this.exmcsRestTemplate = exmcsRestTemplate;
    }

    public ResponseEntity<EmptyResponse> postLogTransaction(SourceOfLogTransaction request) {
        log.info("try to access company url: {}", exmcsRestTemplate);

        return exmcsRestTemplate.exchange(savingLogTransactionUrl,
                HttpMethod.POST,
                new HttpEntity<>(request),
                EmptyResponse.class
        );

    }
}
