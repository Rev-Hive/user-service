package com.revhive.user.service;

import com.revhive.user.dto.request.CreateProfile;
import com.revhive.user.dto.request.UpdateProfile;
import com.revhive.user.dto.response.UserProfile;
import com.revhive.user.dto.response.UserSearchDTO;
import com.revhive.user.model.User;
import com.revhive.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

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

    public UserProfile getCurrentUser(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
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

        user.setBio(request.getBio());
        user.setDob(request.getDob());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setSubscribeNewsletter(
                request.getSubscribeNewsletter()
        );

        user.setStatus(request.getStatus());

        User updatedUser =
                userRepository.save(user);

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
}
