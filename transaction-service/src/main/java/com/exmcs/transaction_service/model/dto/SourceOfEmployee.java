package com.exmcs.transaction_service.model.dto;

import com.exmcs.transaction_service.common.base.BaseResponse;
import com.exmcs.transaction_service.model.dto.EmployeeDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SourceOfEmployee extends BaseResponse {

    private Long employeeId;

    private String pathHierarchyIds;
}
