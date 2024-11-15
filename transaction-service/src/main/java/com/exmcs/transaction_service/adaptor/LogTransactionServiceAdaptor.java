package com.exmcs.transaction_service.adaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LogTransactionServiceAdaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTransactionServiceAdaptor.class);

    private RestTemplate exmcsRestTemplate;

    @Value("${integration.exmcs.log-transaction-service.saving-log-transaction.url}")
    String savingLogTransactionUrl;

    public LogTransactionServiceAdaptor(RestTemplate exmcsRestTemplate) {
        this.exmcsRestTemplate = exmcsRestTemplate;
    }
}
