package com.exmcs.transaction_service.service;

import com.exmcs.transaction_service.adaptor.CompanyServiceAdaptor;
import com.exmcs.transaction_service.common.base.BaseService;
import com.exmcs.transaction_service.model.request.TestRequest;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import com.exmcs.transaction_service.model.dto.SourceOfEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService implements BaseService<TestRequest, EmptyResponse> {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    private final CompanyServiceAdaptor companyServiceAdaptor;

    public TestService(CompanyServiceAdaptor companyServiceAdaptor) {
        this.companyServiceAdaptor = companyServiceAdaptor;
    }

    @Override
    public EmptyResponse execute(TestRequest input) {

        SourceOfEmployee sourceOfEmployee = companyServiceAdaptor.getSourceOfEmployee("5");
        log.info("result Data from Company-service: {}", sourceOfEmployee);

        return null;
    }
}
