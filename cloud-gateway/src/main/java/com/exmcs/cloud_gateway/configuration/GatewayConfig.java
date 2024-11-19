package com.exmcs.cloud_gateway.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("log-service", r -> r.path("/log/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://log-service"))

                .route("login-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://login-service"))

                .route("transaction-service", r -> r.path("/transaction/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://transaction-service"))

                .route("company-service", r -> r.path("/api-fe/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://company-service"))
                .build();
    }
}
