package com.exmcs.login_service.model.response;

import com.exmcs.login_service.common.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostSignoutResponse extends BaseResponse {

    private String message;
}
