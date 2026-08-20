package com.orderflow.login_service.dto.response;

public class ErrorResponse {

    private final String status;
    private final ErrorDetail error;

    public ErrorResponse(String status, ErrorDetail error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public ErrorDetail getError() {
        return error;
    }

    public static ErrorResponse of(String code, String statusCode, String message) {
        return new ErrorResponse(
                "error",
                new ErrorDetail(code, statusCode, message)
        );
    }
}