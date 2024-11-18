package com.exmcs.login_service.service;

import com.exmcs.login_service.common.base.BaseService;
import com.exmcs.login_service.common.util.JwtUtils;
import com.exmcs.login_service.model.request.PostSigninRequest;
import com.exmcs.login_service.model.response.PostSigninResponse;
import com.exmcs.login_service.repository.UserLoginRepository;
import com.exmcs.login_service.service.auth.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSigninService implements BaseService<PostSigninRequest, PostSigninResponse> {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public PostSigninService(AuthenticationManager authenticationManager,
                             JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public PostSigninResponse execute(PostSigninRequest input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        jwtUtils.generateJwtCookie(userDetails);

        return PostSigninResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .tokenType("Bearer")
                .token(jwtUtils.generateTokenAccess(userDetails)).build();
    }
}
