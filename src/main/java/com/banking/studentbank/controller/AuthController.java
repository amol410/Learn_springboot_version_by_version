package com.banking.studentbank.controller;

import com.banking.studentbank.dto.AuthResponse;
import com.banking.studentbank.dto.LoginRequest;
import com.banking.studentbank.dto.RegisterRequest;
import com.banking.studentbank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")   // all endpoints here start with /auth
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // @RequestBody = reads JSON from request body into RegisterRequest object
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}