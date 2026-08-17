package com.orderflow.login_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSelectRequest {
    @NotBlank(message = "storeName is required")
    private String storeName;
    
}
