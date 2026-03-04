package com.banking.studentbank.model;
import jakarta.persistence.*;
import lombok.Data;

@Data                        // Lombok: generates getters, setters, toString
@Entity                      // tells JPA: map this class to a database table
@Table(name = "users")       // table name in MySQL will be "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment ID
    private Long id;

    @Column(unique = true, nullable = false)   // username must be unique
    private String username;

    @Column(nullable = false)
    private String password;

    @Column (unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)  // store role as string "ADMIN" or "CUSTOMER"
    private Role role;

    public enum Role{
        ADMIN, CUSTOMER
    }

}
