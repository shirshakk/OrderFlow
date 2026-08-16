package com.orderservice.orderflow.dto.response;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String code;
    private String statusCode;
    private String message;
}