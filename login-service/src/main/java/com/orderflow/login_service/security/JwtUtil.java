package com.orderflow.login_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ---- Final access token (after full login) ----

    public String generateToken(String getUsername,Claims claims) {
        Long branchId = claims.get("branchId", Long.class);
        Long storeId = claims.get("storeId", Long.class);
        return Jwts.builder()
                .subject(getUsername)
                .claim("purpose", "ACCESS")
                .claim("storeId", storeId)
                .claim("branchId", branchId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ---- Short-lived scoped tokens (store/branch verification steps) ----

    public String generateScopedToken(String purpose, Map<String, Object> claims, long ttlMs) {
        return Jwts.builder()
                .claims(claims)
                .claim("purpose", purpose)
                .subject(purpose)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ttlMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractPurpose(String token) {
        return parseClaims(token).get("purpose", String.class);
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String getUsername) {
        Claims claims = parseClaims(token);
        return claims.getSubject().equals(getUsername)
                && "ACCESS".equals(claims.get("purpose", String.class))
                && !claims.getExpiration().before(new Date());
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ---- validation using ONLY the token's own claims -- no DB, no UserDetails ----

    public boolean isValidAccessToken(String token) {
        try {
            Claims claims = parseClaims(token);
            boolean isAccessToken = "ACCESS".equals(claims.get("purpose", String.class));
            boolean notExpired = claims.getExpiration().after(new Date());
            return isAccessToken && notExpired;
        } catch (Exception e) {
            return false;
        }
    }
}
