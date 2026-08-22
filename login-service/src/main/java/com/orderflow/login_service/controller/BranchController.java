package com.orderflow.login_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.login_service.dto.request.BranchCreate;
import com.orderflow.login_service.dto.response.SuccessResponse;
import com.orderflow.login_service.exception.InvalidScopedTokenException;
import com.orderflow.login_service.model.Store;
import com.orderflow.login_service.repository.StoreRepository;
import com.orderflow.security_common.JwtUtil;
import com.orderflow.login_service.service.BranchService;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class BranchController {

     private final JwtUtil jwtUtil;

     @Autowired
     private StoreRepository storeRepository;

     @Autowired BranchService branchService;
   
    @PostMapping("/branch/create")
    public ResponseEntity<SuccessResponse<String>> createBranch(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody BranchCreate request
        ) {

        String storeToken = extractBearerToken(authHeader);
        Claims claims = requireScopedToken(storeToken, "STORE_VERIFIED");
        Long storeId = claims.get("storeId", Long.TYPE);
        Store store=storeRepository.findByid(storeId);
        branchService.CreateBranch(store, request.getPassword(), request.getAddress(), request.getCity(), request.getState(), request.getPincode(), request.getPhone(), request.getCountry(), storeId);

        return ResponseEntity.ok(
                SuccessResponse.of("Token extracted successfully")
        );
    }


    // ---- helpers ----

    private String extractBearerToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidScopedTokenException("Missing or malformed Authorization header");
        }
        return authHeader.substring(7);
    }

    private Claims requireScopedToken(String token, String expectedPurpose) {
        Claims claims;
        try {
            claims = jwtUtil.parseClaims(token);
        } catch (Exception e) {
            throw new InvalidScopedTokenException("Token is invalid or expired");
        }

        String purpose = claims.get("purpose", String.class);
        if (!expectedPurpose.equals(purpose)) {
            throw new InvalidScopedTokenException("Token is not valid for this step");
        }

        return claims;
    }
}