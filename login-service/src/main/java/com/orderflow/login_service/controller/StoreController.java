package com.orderflow.login_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.login_service.dto.request.StoreCreate;
import com.orderflow.login_service.dto.response.SuccessResponse;
import com.orderflow.login_service.service.StoreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class StoreController {

    @Autowired
    private StoreService storeService;


    @PostMapping("/createstore")
    public ResponseEntity<SuccessResponse<String>> createStore(
        @Valid @RequestBody StoreCreate request ){
        String message = storeService.createStore(request.getStoreName(), request.getEmail(), request.getPhone(),
                request.getFirstName(), request.getLastName(), request.getPassword());
        
        return ResponseEntity.ok().body(SuccessResponse.of(message));
    }
}
