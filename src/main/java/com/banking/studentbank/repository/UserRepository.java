package com.banking.studentbank.repository;

import com.banking.studentbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository gives us save(), findById(), findAll(), delete() for free
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring auto-generates SQL: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);

    // Spring auto-generates SQL: SELECT * FROM users WHERE email = ?
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
