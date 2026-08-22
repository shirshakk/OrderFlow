package com.orderflow.product_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.login_service.exception.InvalidScopedTokenException;
import com.orderflow.login_service.security.JwtUtil;
import com.orderflow.product_service.dto.request.CreateCategoryRequest;
import com.orderflow.product_service.dto.response.SuccessResponse;
import com.orderflow.product_service.service.CategoryService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private final JwtUtil jwtUtil;
    @PostMapping("/category/create")
    public ResponseEntity<SuccessResponse<String>> createCategory(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody CreateCategoryRequest request
    ){
        String token=extractBearerToken(authHeader);
        Claims claims= requireScopedToken(token,"BRANCH_VERIFIED");
        String message=categoryService.createCategory(claims, authHeader, authHeader);
        return ResponseEntity.ok(SuccessResponse.of(message));

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
