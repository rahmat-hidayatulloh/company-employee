package com.exmcs.company_service.exception;

import com.exmcs.company_service.model.response.GlobalDtoResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<GlobalDtoResponse<String>> dataHandleNotFound(BussinessException e){
        return new ResponseEntity<>(new GlobalDtoResponse<String>(
                e.getMessage(),null),
                HttpStatus.NOT_FOUND
        );
    }
}
