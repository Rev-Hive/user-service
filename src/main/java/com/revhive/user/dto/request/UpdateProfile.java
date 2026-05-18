package com.revhive.user.dto.request;


import com.revhive.user.validation.Adult;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfile {

    @Size(min = 10, max = 100,
            message = "Bio must be between 10 to 100 characters")
    private String bio;

    @Past(message = "DOB must be in the past")
    @Adult
    private LocalDate dob;

    private Boolean subscribeNewsletter;

    private String avatarUrl;

    private String status;
}
