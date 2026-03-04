package com.banking.studentbank.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")  // reads 86400000 from application.properties
    private long expiration;

    // converts secret string into a cryptographic Key object
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());

    }

    // creates a JWT token for a given username and role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)                          // who this token belongs to
                .claim("role", role)                           // extra data inside token
                .setIssuedAt(new Date())                       // when created
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // when expires
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // sign with secret
                .compact();
    }

    // reads username out of a token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // reads role out of a token
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // checks if token is expired
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);  // throws exception if invalid or expired
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
