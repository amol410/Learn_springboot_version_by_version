
package com.banking.studentbank.service;
import com.banking.studentbank.dto.AuthResponse;
import com.banking.studentbank.dto.LoginRequest;
import com.banking.studentbank.dto.RegisterRequest;
import com.banking.studentbank.model.User;
import com.banking.studentbank.repository.UserRepository;
import com.banking.studentbank.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service             // Spring manages this as a service bean
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        // check if username already taken
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // check if email already taken
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

// build User object
User user = new User();
          user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encrypt!
        user.setEmail(request.getEmail());
        user.setRole(User.Role.CUSTOMER);  // default role = CUSTOMER

          userRepository.save(user);  // INSERT into users table

// generate token immediately after register
String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
          return new AuthResponse(token, user.getUsername(), user.getRole().name());
        }

public AuthResponse login(LoginRequest request) {

    // find user by username
    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    // compare raw password with stored BCrypt hash
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    return new AuthResponse(token, user.getUsername(), user.getRole().name());
}
  }