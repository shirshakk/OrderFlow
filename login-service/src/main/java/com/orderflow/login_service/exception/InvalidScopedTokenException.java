package com.orderflow.login_service.exception;

public class InvalidScopedTokenException extends RuntimeException {
    public InvalidScopedTokenException(String message) {
        super(message);
    }
}
