package com.orderflow.login_service.dto.response;

public class ErrorDetail {
    private String code;
    private String statusCode;
    private String message;

    public ErrorDetail(String code, String statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
