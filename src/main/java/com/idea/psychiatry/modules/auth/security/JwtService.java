package com.idea.psychiatry.modules.auth.security;


import com.idea.psychiatry.modules.auth.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String CLAIM_USER_ID   = "userId";
    private static final String CLAIM_ORG_ID    = "organizationId";
    private static final String CLAIM_ROLE      = "role";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    // ── Token Generation ──────────────────────

    public String generateAccessToken(CustomUserPrincipal principal) {
        return buildToken(principal, jwtProperties.expirationMs());
    }

    public String generateRefreshToken(CustomUserPrincipal principal) {
        return buildToken(principal, jwtProperties.refreshExpirationMs());
    }

    private String buildToken(CustomUserPrincipal principal, long expirationMs) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(principal.getUsername())
                .claim(CLAIM_USER_ID, principal.getUserId().toString())
                .claim(CLAIM_ORG_ID,  principal.getOrganizationId().toString())
                .claim(CLAIM_ROLE,    principal.getRole())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    // ── Token Validation ──────────────────────

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ── Claims Extraction ─────────────────────

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).get(CLAIM_USER_ID, String.class));
    }

    public UUID extractOrganizationId(String token) {
        return UUID.fromString(parseClaims(token).get(CLAIM_ORG_ID, String.class));
    }

    public String extractRole(String token) {
        return parseClaims(token).get(CLAIM_ROLE, String.class);
    }

    // ── private ───────────────────────────────

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
