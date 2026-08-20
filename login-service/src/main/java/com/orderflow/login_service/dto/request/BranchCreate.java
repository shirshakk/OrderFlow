package com.orderflow.login_service.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchCreate {
   
    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotBlank(message = "Enter the address of the branch")
    private String address;

    @NotBlank(message = "Enter the city")
    private String city;

    @NotBlank(message = "Enter the state")
    private String state; 

    @NotBlank(message = "Enter the pincode")
    private String pincode;

    @NotBlank(message = "Enter the phone number of the store")
    private String phone;

    @NotBlank(message = "Enter the country")
    private String country;
}
