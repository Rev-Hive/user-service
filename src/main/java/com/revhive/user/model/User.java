package com.revhive.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{

    @Id
    @Column(name = "id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String bio;

    private String avatarUrl;

    @Builder.Default
    private int followersCount = 0;

    @Builder.Default
    private int followingCount = 0;

    private boolean premium;

    private LocalDate premiumExpiry;

    @Past(message = "DOB must be in the past")
    private LocalDate dob;




    private Boolean subscribeNewsletter;

    private String status;

    private String password;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
