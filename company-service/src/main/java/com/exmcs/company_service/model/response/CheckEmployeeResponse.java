package com.exmcs.company_service.model.response;

import com.exmcs.company_service.common.base.BaseResponse;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckEmployeeResponse extends BaseResponse {

    private boolean isExistEmployee;
}
