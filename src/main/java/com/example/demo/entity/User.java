package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String role; // ADMIN, USER, MANAGER

    @Column(nullable = false, length = 20)
    private String status; // 활성, 비활성

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "last_login")
    private LocalDate lastLogin;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}

