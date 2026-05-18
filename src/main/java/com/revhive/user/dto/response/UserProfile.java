package com.revhive.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UserProfile {

    private Long userId;

    private String username;

    private String email;

    private String bio;

    private String avatarUrl;

    private int followersCount;

    private int followingCount;

    private boolean premium;

    private LocalDate premiumExpiry;

    private LocalDate dob;

    private Boolean subscribeNewsletter;

    private String status;
}
