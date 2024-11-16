package com.exmcs.company_service.controller;

import com.exmcs.company_service.model.request.EmployeeRequest;
import com.exmcs.company_service.model.request.UniversalIdRequest;
import com.exmcs.company_service.model.response.CheckEmployeeResponse;
import com.exmcs.company_service.model.response.GetEmployeeListResponse;
import com.exmcs.company_service.model.response.GetEmployeeResponse;
import com.exmcs.company_service.service.CheckEmployeeService;
import com.exmcs.company_service.service.GetEmployeeListService;
import com.exmcs.company_service.service.GetEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-fe")
public class EmployeeController {

    private final GetEmployeeListService getEmployeeListService;
    private final GetEmployeeService getEmployeeService;
    private final CheckEmployeeService checkEmployeeService;

    public EmployeeController(GetEmployeeListService getEmployeeListService,
                              GetEmployeeService getEmployeeService,
                              CheckEmployeeService checkEmployeeService) {
        this.getEmployeeListService = getEmployeeListService;
        this.getEmployeeService = getEmployeeService;
        this.checkEmployeeService = checkEmployeeService;
    }

    @GetMapping("/list-employee")
    public GetEmployeeListResponse getEmployeeList(){
        return getEmployeeListService.execute(new EmployeeRequest());
    }

    @GetMapping("/get-employee/{employeeId}")
    public GetEmployeeResponse getEmployee(@PathVariable Long employeeId) {
        return getEmployeeService.execute(UniversalIdRequest.builder()
                .id(employeeId)
                .build());
    }

    @GetMapping("/checking-employee/{employeeId}")
    public CheckEmployeeResponse checkEmployeeResponse(@PathVariable Long employeeId){
        return checkEmployeeService.execute(UniversalIdRequest.builder()
                .id(employeeId)
                .build());
    }

}
