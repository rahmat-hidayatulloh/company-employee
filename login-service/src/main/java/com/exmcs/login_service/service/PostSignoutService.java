package com.exmcs.login_service.service;

import com.exmcs.login_service.common.base.BaseRequest;
import com.exmcs.login_service.common.base.BaseService;
import com.exmcs.login_service.common.util.JwtUtils;
import com.exmcs.login_service.model.response.PostSignoutResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PostSignoutService implements BaseService<BaseRequest, PostSignoutResponse> {

    private final JwtUtils jwtUtils;

    public PostSignoutService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public PostSignoutResponse execute(BaseRequest input) {

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtUtils.getCleanJwtCookie().toString())
                .body(new PostSignoutResponse("You've been signed out!")).getBody();
    }
}
