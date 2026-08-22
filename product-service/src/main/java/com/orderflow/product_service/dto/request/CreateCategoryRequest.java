package com.orderflow.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequest {
    @NotBlank(message = "Category name can not be null.")
    private String name;
    private String description;
    
}
