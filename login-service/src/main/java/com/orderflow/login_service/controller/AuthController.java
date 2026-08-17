package com.orderflow.login_service.controller;

import com.orderflow.login_service.dto.request.BranchVerifyRequest;
import com.orderflow.login_service.dto.request.EmployeeLoginRequest;
import com.orderflow.login_service.dto.request.StoreSelectRequest;
import com.orderflow.login_service.dto.response.AuthResponse;
import com.orderflow.login_service.dto.response.BranchVerifyResponse;
import com.orderflow.login_service.dto.response.StoreSelectResponse;
import com.orderflow.login_service.dto.response.SuccessResponse;
import com.orderflow.login_service.exception.BranchVerificationException;
import com.orderflow.login_service.exception.InvalidScopedTokenException;
import com.orderflow.login_service.exception.StoreNotFoundException;
import com.orderflow.login_service.model.Branch;
import com.orderflow.login_service.model.Employee;
import com.orderflow.login_service.model.Role;
import com.orderflow.login_service.model.Status;
import com.orderflow.login_service.model.Store;
import com.orderflow.login_service.repository.BranchRepository;
import com.orderflow.login_service.repository.EmployeeRepository;
import com.orderflow.login_service.repository.StoreRepository;
import com.orderflow.login_service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final long STORE_TOKEN_TTL_MS = 5 * 60 * 1000;   // 5 minutes
    private static final long BRANCH_TOKEN_TTL_MS = 5 * 60 * 1000;  // 5 minutes

    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ---- Step 1: store name -> storeToken ----

    @PostMapping("/store/select")
    public ResponseEntity<SuccessResponse<StoreSelectResponse>> selectStore(
            @Valid @RequestBody StoreSelectRequest request) {

        Store store = storeRepository.findByNameIgnoreCase(request.getStoreName())
                .orElseThrow(() -> new StoreNotFoundException(request.getStoreName()));

        Map<String, Object> claims = new HashMap<>();
        claims.put("storeId", store.getId());

        String storeToken = jwtUtil.generateScopedToken("STORE_VERIFIED", claims, STORE_TOKEN_TTL_MS);

        StoreSelectResponse response = new StoreSelectResponse(storeToken, store.getName());
        return ResponseEntity.ok(SuccessResponse.of(response));
    }

    // ---- Step 2: storeToken (header) + branch code/password (body) -> branchToken ----

    @PostMapping("/branch/verify")
    public ResponseEntity<SuccessResponse<BranchVerifyResponse>> verifyBranch(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody BranchVerifyRequest request) {

        String storeToken = extractBearerToken(authHeader);
        Claims claims = requireScopedToken(storeToken, "STORE_VERIFIED");
        Long storeId = claims.get("storeId", Long.class);

        Branch branch = branchRepository.findByCodeAndStoreId(request.getBranchCode(), storeId)
                .orElseThrow(() -> new BranchVerificationException("Invalid branch code"));

        if (!passwordEncoder.matches(request.getBranchPassword(), branch.getPassword())) {
            throw new BranchVerificationException("Invalid branch password");
        }

        if (branch.getStatus() != Status.ACTIVE) {
            throw new BranchVerificationException("Branch is not active");
        }
        claims.put("storeId", storeId);
        claims.put("branchId", branch.getId());

        String branchToken = jwtUtil.generateScopedToken("BRANCH_VERIFIED", claims, BRANCH_TOKEN_TTL_MS);

        BranchVerifyResponse response = new BranchVerifyResponse(branchToken, branch.getCode(), branch.getCity());
        return ResponseEntity.ok(SuccessResponse.of(response));
    }

    // ---- Step 3: branchToken (header) + PIN (body) -> final access token ----

    @PostMapping("/employee/login")
    public ResponseEntity<SuccessResponse<AuthResponse>> employeeLogin(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody EmployeeLoginRequest request) {

        String branchToken = extractBearerToken(authHeader);
        Claims claims = requireScopedToken(branchToken, "BRANCH_VERIFIED");
        Long branchId = claims.get("branchId", Long.class);

        List<Employee> branchEmployees = employeeRepository.findAllByBranchIdAndStatus(branchId, Status.ACTIVE);

        Employee employee = branchEmployees.stream()
                .filter(e -> passwordEncoder.matches(request.getPin(), e.getPin()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PIN"));

        String token = jwtUtil.generateToken(employee);
        AuthResponse response = new AuthResponse(
                token,
                employee.getFirstName(),
                employee.getRole(),
                employee.getBranch() != null ? employee.getBranch().getCode() : null
        );

        return ResponseEntity.ok(SuccessResponse.of(response));
    }


    @PostMapping("/admin/login")
    public ResponseEntity<SuccessResponse<AuthResponse>> adminLogin(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody EmployeeLoginRequest request) {

        String storeToken = extractBearerToken(authHeader);
        Claims claims = requireScopedToken(storeToken, "STORE_VERIFIED");
        Long storeId = claims.get("storeId", Long.class);

        List<Employee> storeEmployees = employeeRepository.findAllByBranchIdAndStatus(storeId, Status.ACTIVE);

        Employee employee = storeEmployees.stream()
                .filter(e -> e.getRole() == Role.ADMIN && passwordEncoder.matches(request.getPin(), e.getPin()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PIN or not an admin"));

        String token = jwtUtil.generateToken(employee);
        AuthResponse response = new AuthResponse(
                token,
                employee.getFirstName(),
                employee.getRole(),
                employee.getBranch() != null ? employee.getBranch().getCode() : null
        );

        return ResponseEntity.ok(SuccessResponse.of(response));
    }

    @PostMapping("/employee/logout")
    public ResponseEntity<SuccessResponse<String>> employeeLogout() {
        // In a stateless JWT authentication system, logout is typically handled on the client side
        // by simply deleting the token. However, if you want to implement server-side logout,
        // you can maintain a blacklist of tokens or use a token revocation strategy.

        return ResponseEntity.ok(SuccessResponse.of("Logout successful"));
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
