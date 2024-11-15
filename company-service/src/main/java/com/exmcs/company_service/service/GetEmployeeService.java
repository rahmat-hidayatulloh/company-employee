package com.exmcs.company_service.service;

import com.exmcs.company_service.common.base.BaseService;
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
        List<Object[]> hierarchies = employeeRepository.getEmployeeHierarchyById(input.getId());

        if (hierarchies == null || hierarchies.isEmpty()) {
            return new GetEmployeeResponse(null, null);
        }

        List<EmployeeDto> employeeDtos = new ArrayList<>();

        String pathHierarchyIds = null;
        for (Object[] result : hierarchies) {
            Long employeeId = (result[0] instanceof Long) ? (Long) result[0] : Long.valueOf((Integer) result[0]);
            String employeeName = (String) result[1];
            String managerName = (String) result[2];
            String employeeFormat = (String) result[3];
            String pathHierarchy = (String) result[4];
            pathHierarchyIds = (String) result[5];
            Integer pathLevel = (Integer) result[6];

            EmployeeDto dto = new EmployeeDto(employeeId, employeeName, managerName, employeeFormat, pathHierarchy, pathLevel);
            employeeDtos.add(dto);
        }

        EmployeeDto employeeDto = employeeDtos.isEmpty() ? null : employeeDtos.get(0);

/*        List<Long> hierarchyIdList = pathHierarchyString != null ?
                Arrays.stream(pathHierarchyString.split(" > "))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()) :
                Collections.emptyList();*/

        return new GetEmployeeResponse(employeeDto, pathHierarchyIds);
    }

}
