package com.orderflow.product_service.dto.request;

import org.antlr.v4.runtime.misc.NotNull;

public class updateProductRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Long categoryId;

    @NotNull
    private ProductStatus status;

    public enum ProductStatus {
        ACTIVE,
        INACTIVE
    }
}
