package com.orderflow.product_service.dto.request;

import org.antlr.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.util.PerformanceSensitive;

public class createProductRequest {
    @NotNull
    private String sku;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Long categoryId;
}
