package com.exmcs.company_service.model.response;

import com.exmcs.company_service.common.base.BaseResponse;
import com.exmcs.company_service.model.dto.EmployeeDto;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetEmployeeListResponse extends BaseResponse {
    private List<EmployeeDto> employees;
}
