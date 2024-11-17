package com.exmcs.company_service.exception;

public class BussinessException extends RuntimeException{

    public BussinessException(String key, Long id) {
        super("id "+key+" "+id+" Not Found");
    }
}
