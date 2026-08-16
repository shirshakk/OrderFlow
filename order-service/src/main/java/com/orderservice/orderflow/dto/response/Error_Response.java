package com.orderservice.orderflow.dto.response;

public record Error_Response(String status, ErrorDetail error) {

    public static Error_Response of(String code, String statusCode, String message) {
        return new Error_Response(
                "error",
                new ErrorDetail(code, statusCode, message)
        );
    }
}