package com.revhive.user.dto.request;
import com.revhive.user.validation.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateProfile{

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Bio is required")
    @Size(min = 10, max = 100,
            message = "Bio must be between 10 to 100 characters")
    private String bio;

    @Past(message = "DOB must be in the past")
    @Adult
    private LocalDate dob;

    private Boolean subscribeNewsletter;

    private String avatarUrl;
}
