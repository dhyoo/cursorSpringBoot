package com.softone.prj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "board")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(name = "author_email", length = 100)
    private String authorEmail;

    @Column
    private Long views;

    @Column(length = 50)
    private String category;

    @Column(length = 20)
    private String status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDate.now();
        }
        if (views == null) {
            views = 0L;
        }
    }
}
