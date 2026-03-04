package com.banking.studentbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // Lombok: generates constructor with all fields
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}
