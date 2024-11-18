package com.exmcs.login_service.configuration;

import java.io.IOException;

import com.exmcs.login_service.common.util.JwtUtils;
import com.exmcs.login_service.service.auth.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {


    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    private final JwtUtils jwtUtils;

    @Value("${configuration.jwt.jwt-token-type.value}")
    private String jwtTokenType;

    private final UserDetailsServiceImpl userDetailsService;

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = parseJwt(request);

            if (jwt != null || jwtUtils.validateJwtToken(jwt)) {

                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenType))
        {return bearerToken.substring(7); }
        return null;
    }
}
