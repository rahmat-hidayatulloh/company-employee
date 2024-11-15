package com.exmcs.company_service.model.request;

import com.exmcs.company_service.common.base.BaseRequest;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmployeeRequest extends BaseRequest {
    private Long id;
}
