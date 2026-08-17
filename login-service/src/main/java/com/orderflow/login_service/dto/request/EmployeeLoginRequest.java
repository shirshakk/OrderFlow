package com.orderflow.login_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeLoginRequest {

    @NotBlank(message = "pin is required")
    @Pattern(regexp = "\\d{4}", message = "pin must be exactly 4 digits")
    private String pin;

    public EmployeeLoginRequest() {
    }

    public EmployeeLoginRequest(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

