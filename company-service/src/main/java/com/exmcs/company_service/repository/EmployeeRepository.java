package com.exmcs.company_service.repository;

import com.exmcs.company_service.model.dto.EmployeeDto;
import com.exmcs.company_service.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Procedure(procedureName = "sp_GetEmployeeHierarchy")
    List<Object[]> getEmployeeHierarchy();

    @Procedure(procedureName = "sp_GetIdWIthEmployeeHierarchyById")
    List<Object[]> getIdWIthEmployeeHierarchyById(@Param("EmployeeId") Long employeeId);

    @Procedure(procedureName = "sp_GetHierarchyIdList")
    String getHierarchyPathById(@Param("EmployeeId") Long employeeId);
}
