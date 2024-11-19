package com.exmcs.cloud_gateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndPoints = List.of(
            "/auth/signin"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
