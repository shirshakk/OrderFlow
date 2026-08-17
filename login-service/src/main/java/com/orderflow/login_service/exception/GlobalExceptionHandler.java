package com.orderflow.login_service.exception;

import com.orderflow.login_service.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStoreNotFound(StoreNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("STORE_NOT_FOUND", "404", ex.getMessage()));
    }

    @ExceptionHandler(BranchVerificationException.class)
    public ResponseEntity<ErrorResponse> handleBranchVerification(BranchVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of("BRANCH_VERIFICATION_FAILED", "401", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPin(InvalidPinException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of("INVALID_PIN", "401", ex.getMessage()));
    }

    @ExceptionHandler(InvalidScopedTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidScopedToken(InvalidScopedTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of("INVALID_TOKEN", "401", ex.getMessage()));
    }
}
