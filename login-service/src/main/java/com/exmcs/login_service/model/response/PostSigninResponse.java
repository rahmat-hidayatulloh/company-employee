package com.exmcs.login_service.model.response;


import com.exmcs.login_service.common.base.BaseResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostSigninResponse extends BaseResponse {

    private Long id;

    private String username;

    private String token;

    private String tokenType;
}
