package com.exmcs.transaction_service.exception;

public class InvalidateFormatException extends RuntimeException {

    public InvalidateFormatException(String value, String type) {
        super(value + " must be " + type);
    }
}
