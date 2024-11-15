package com.exmcs.transaction_service.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmployeeDto {

    private Long employeeId;

    private String employeeName;

    private String managerName;

    private String employeeFormat;

    private String pathHierarchy;

    private Integer pathLevel;
}
