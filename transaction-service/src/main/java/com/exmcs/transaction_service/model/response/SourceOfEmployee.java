package com.exmcs.transaction_service.model.response;

import com.exmcs.transaction_service.common.base.BaseResponse;
import com.exmcs.transaction_service.model.dto.EmployeeDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SourceOfEmployee extends BaseResponse {

    private EmployeeDto employee;

    private String pathHierarchyIds;
}
