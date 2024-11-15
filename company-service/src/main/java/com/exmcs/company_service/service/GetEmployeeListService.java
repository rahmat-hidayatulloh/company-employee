package com.exmcs.company_service.service;

import com.exmcs.company_service.common.base.BaseService;
import com.exmcs.company_service.model.dto.EmployeeDto;
import com.exmcs.company_service.model.request.EmployeeRequest;
import com.exmcs.company_service.model.response.GetEmployeeListResponse;
import com.exmcs.company_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetEmployeeListService implements BaseService<EmployeeRequest, GetEmployeeListResponse> {

    private final Logger logger = LoggerFactory.getLogger(GetEmployeeListService.class);

    private final EmployeeRepository employeeRepository;

    public GetEmployeeListService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public GetEmployeeListResponse execute(EmployeeRequest input) {
        return getAllOfEmployee();
    }

    private GetEmployeeListResponse getAllOfEmployee() {
        List<Object[]> results = employeeRepository.getEmployeeHierarchy();
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        for (Object[] row : results) {
            EmployeeDto dto = new EmployeeDto();
            dto.setEmployeeId(((Number) row[0]).longValue());
            dto.setEmployeeName((String) row[1]);
            dto.setManagerName((String) row[2]);
            dto.setEmployeeFormat((String) row[3]);
            dto.setPathHierarchy((String) row[4]);
            dto.setPathLevel((Integer) row[5]);
            employeeDtos.add(dto);
        }
        return GetEmployeeListResponse.builder()
                .employees(employeeDtos)
                .build();
    }
}
