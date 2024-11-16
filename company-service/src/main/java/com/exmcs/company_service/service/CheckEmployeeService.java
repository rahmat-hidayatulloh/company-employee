package com.exmcs.company_service.service;

import com.exmcs.company_service.common.base.BaseService;
import com.exmcs.company_service.model.request.UniversalIdRequest;
import com.exmcs.company_service.model.response.CheckEmployeeResponse;
import com.exmcs.company_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckEmployeeService implements BaseService<UniversalIdRequest, CheckEmployeeResponse> {

    private final EmployeeRepository employeeRepository;

    public CheckEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CheckEmployeeResponse execute(UniversalIdRequest input) {
        return CheckEmployeeResponse.builder()
                .isExistEmployee(employeeRepository.existsById(input.getId()))
                .build();
    }
}
