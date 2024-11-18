package com.exmcs.transaction_service.adaptor;

import com.exmcs.transaction_service.exception.BussinessException;
import com.exmcs.transaction_service.model.dto.SourceOfEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CompanyServiceAdaptor {

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceAdaptor.class);

    @Value("${integration.exmcs.company-service.getting-employee-data.url}")
    String companyEmployeeByIdUrl;

    @Value("${integration.exmcs.company-service.checking-employee.url}")
    String checkingEmloyeeByIdUrl;

    private RestTemplate exmcsRestTemplate;

    public CompanyServiceAdaptor(RestTemplate exmcsRestTemplate) {
        this.exmcsRestTemplate = exmcsRestTemplate;
    }

    public SourceOfEmployee getSourceOfEmployee(String employeeId){
        log.info("try to access company url: {}", companyEmployeeByIdUrl);

        StringBuilder sb = new StringBuilder(companyEmployeeByIdUrl)
                .append("/")
                .append(employeeId);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            return exmcsRestTemplate.exchange(sb.toString(), HttpMethod.GET, entity, SourceOfEmployee.class).getBody();

        }catch (HttpClientErrorException.NotFound ex){
            throw new BussinessException(ex.getResponseBodyAsString());
        }
    }

}
