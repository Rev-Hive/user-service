package com.revhive.user.dto.request;

import com.revhive.user.validation.Adult;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfile {

    @Size(min = 3, max = 30,
            message = "Username must be between 3 and 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can only contain letters, numbers, dots and underscores"
    )
    private String username;

    @Size(max = 100,
            message = "Bio must not exceed 100 characters")
    private String bio;

    @Past(message = "DOB must be in the past")
    @Adult
    private LocalDate dob;

    private Boolean subscribeNewsletter;

    private String avatarUrl;

    private String status;
}
