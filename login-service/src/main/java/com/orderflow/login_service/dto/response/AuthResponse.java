package com.orderflow.login_service.dto.response;

import com.orderflow.login_service.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private final String token;
    private final String firstName;
    private final Role role;
    private final String branchCode;

    public AuthResponse(String token, String firstName, Role role, String branchCode) {
        this.token = token;
        this.firstName = firstName;
        this.role = role;
        this.branchCode = branchCode;
    }

    public String token() {
        return token;
    }

    public String firstName() {
        return firstName;
    }

    public Role role() {
        return role;
    }

    public String branchCode() {
        return branchCode;
    }
}
