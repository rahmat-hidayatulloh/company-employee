package com.exmcs.login_service.model.request;

import com.exmcs.login_service.common.base.BaseRequest;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostSigninRequest extends BaseRequest {

    private String username;
    private String password;
}
