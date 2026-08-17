package com.orderflow.login_service.dto.response;

public class BranchVerifyResponse {
    private String branchToken;
    private String branchCode;
    private String city;

    public BranchVerifyResponse(String branchToken, String branchCode, String city) {
        this.branchToken = branchToken;
        this.branchCode = branchCode;
        this.city = city;
    }

    public String getBranchToken() {
        return branchToken;
    }

    public void setBranchToken(String branchToken) {
        this.branchToken = branchToken;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
