package com.orderflow.login_service.exception;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(String name) {
        super(new StringBuilder().append("Store not found: ").append(name).toString());
    }
}
