package com.revhive.user.service;

import com.revhive.user.dto.request.ChangePasswordRequest;
import com.revhive.user.dto.request.CreateProfile;
import com.revhive.user.dto.request.UpdateProfile;
import com.revhive.user.dto.response.UserProfile;
import com.revhive.user.dto.response.UserSearchDTO;
import com.revhive.user.model.User;
import com.revhive.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfile createProfile(
            CreateProfile request
    ) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .bio(request.getBio())
                .dob(request.getDob())
                .avatarUrl(request.getAvatarUrl())
                .subscribeNewsletter(
                        request.getSubscribeNewsletter()
                )
                .build();

        User savedUser = userRepository.save(user);

        logger.info(
                "Profile created successfully for {}",
                savedUser.getEmail()
        );

        return mapToResponse(savedUser);
    }

    public UserProfile getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + email
                        )
                );

        return mapToResponse(user);
    }
    public UserProfile getProfileById(Long userId) {

        System.out.println("Searching userId = " + userId);

        System.out.println(
                userRepository.findUserByUserId(userId)
        );

        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        return mapToResponse(user);
    }

    public UserProfile updateProfile(
            String email,
            UpdateProfile request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        if (request.getUsername() != null &&
                !request.getUsername().equals(user.getUsername())) {

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            user.setUsername(request.getUsername());
        }

        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        if (request.getSubscribeNewsletter() != null) {
            user.setSubscribeNewsletter(
                    request.getSubscribeNewsletter()
            );
        }

        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }
    public List<UserSearchDTO> searchUsers(
            String query
    ) {

        List<User> users =
                userRepository
                        .findTop10ByUsernameContainingIgnoreCase(query);

        return users.stream()
                .map(user -> new UserSearchDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getAvatarUrl()
                ))
                .toList();
    }

    private UserProfile mapToResponse(
            User user
    ) {

        return UserProfile.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .premium(user.isPremium())
                .premiumExpiry(user.getPremiumExpiry())
                .dob(user.getDob())
                .subscribeNewsletter(
                        user.getSubscribeNewsletter()
                )
                .status(user.getStatus())
                .build();
    }



    public void changePassword(String email, ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }

}
