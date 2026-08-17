package com.orderflow.login_service.dto.response;

import java.time.Instant;

public class SuccessResponse<T> {
    private final String status;
    private final T data;
    private final Instant timestamp;

    public SuccessResponse(String status, T data, Instant timestamp) {
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String status() {
        return status;
    }

    public T data() {
        return data;
    }

    public Instant timestamp() {
        return timestamp;
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>("success", data, Instant.now());
    }
}
