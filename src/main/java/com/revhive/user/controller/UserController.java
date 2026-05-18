package com.revhive.user.controller;

import com.revhive.user.dto.request.CreateProfile;
import com.revhive.user.dto.request.UpdateProfile;
import com.revhive.user.dto.response.UserProfile;
import com.revhive.user.dto.response.UserSearchDTO;
import com.revhive.user.model.User;
import com.revhive.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "USER SERVICE", description = "User Profile APIs")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create user profile")
    @PostMapping("/create-profile")
    public ResponseEntity<UserProfile> createProfile(
            @Valid @RequestBody CreateProfile request
    ) {

        return ResponseEntity.ok(
                userService.createProfile(request)
        );
    }

    @Operation(summary = "Get user profile by ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUserProfile(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                userService.getProfileById(userId)
        );
    }

    @Operation(summary = "Get current logged in user")
    @GetMapping("/me")
    public ResponseEntity<UserProfile> getCurrentUser(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                userService.getCurrentUser(
                        authentication.getName()
                )
        );
    }

    @Operation(summary = "Update profile")
    @PutMapping("/profile")
    public ResponseEntity<UserProfile> updateProfile(
            @Valid @RequestBody UpdateProfile request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        authentication.getName(),
                        request
                )
        );
    }

    @Operation(summary = "Search users")
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchDTO>> searchUsers(
            @RequestParam String query
    ) {

        return ResponseEntity.ok(
                userService.searchUsers(query)
        );
    }
}
