package com.orderservice.orderflow.dto.response;

import java.time.Instant;

public record SuccessResponse<T>(String status, T data, Instant timestamp) {

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>("success", data, Instant.now());
    }
}