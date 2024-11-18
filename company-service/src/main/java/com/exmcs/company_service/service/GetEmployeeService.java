package com.exmcs.company_service.service;

import com.exmcs.company_service.common.base.BaseService;
import com.exmcs.company_service.exception.BussinessException;
import com.exmcs.company_service.model.dto.EmployeeDto;
import com.exmcs.company_service.model.request.UniversalIdRequest;
import com.exmcs.company_service.model.response.GetEmployeeResponse;
import com.exmcs.company_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetEmployeeService implements BaseService<UniversalIdRequest, GetEmployeeResponse> {

    private static final Logger log = LoggerFactory.getLogger(GetEmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public GetEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public GetEmployeeResponse execute(UniversalIdRequest input) {

        List<Object[]> hierarchies = employeeRepository.getIdWIthEmployeeHierarchyById(input.getId());

        if (hierarchies == null || hierarchies.isEmpty()) {
            /*return new GetEmployeeResponse(null, null);*/
            throw new BussinessException("Company id " + input.getId() + " Not Found!");
        }

        String pathHierarchyIds = null;
        Long employeeId = Long.valueOf(0);
        for (Object[] result : hierarchies) {
            employeeId = (result[0] instanceof Long) ? (Long) result[0] : Long.valueOf((Integer) result[0]);
            pathHierarchyIds = (String) result[1];
        }

        return new GetEmployeeResponse(employeeId, pathHierarchyIds);
    }

}
