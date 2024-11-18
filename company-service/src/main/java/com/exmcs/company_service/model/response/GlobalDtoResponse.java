package com.exmcs.company_service.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GlobalDtoResponse<T>(
        T error,
        T data
) {
}
