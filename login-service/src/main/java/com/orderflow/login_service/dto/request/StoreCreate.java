package com.orderflow.login_service.dto.request;

import com.orderflow.login_service.model.Role;

import ch.qos.logback.core.status.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreCreate {
    
    private String storeName;


    private String email;

    private String phone;



    private String firstName;

    private String lastName;

    private String password;


}
