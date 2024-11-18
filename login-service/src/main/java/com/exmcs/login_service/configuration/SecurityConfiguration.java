package com.exmcs.login_service.configuration;

import com.exmcs.login_service.service.auth.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private final AuthTokenFilter authTokenFilter;


    @Bean
    public AuthTokenFilter autheticationJwtTokenFilter() {

        return authTokenFilter;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO Auto-generated method stub
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
            )
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/v3/**").permitAll()
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

}
