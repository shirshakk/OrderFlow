package com.orderservice.orderflow.dto.response;

public class Error_Response {
    private final String status;
    private final ErrorDetail error;

    public Error_Response(String status, ErrorDetail error) {
        this.status = status;
        this.error = error;
    }

    public String status() {
        return status;
    }

    public ErrorDetail error() {
        return error;
    }

    public static Error_Response of(String code, String statusCode, String message) {
        return new Error_Response(
                "error",
                new ErrorDetail(code, statusCode, message)
        );
    }
}