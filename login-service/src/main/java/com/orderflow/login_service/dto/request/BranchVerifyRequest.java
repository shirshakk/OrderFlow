package com.orderflow.login_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchVerifyRequest {
    @NotBlank(message = "branchCode is required")
    private String branchCode;

    @NotBlank(message = "branchPassword is required")
    private String branchPassword;

    
}
