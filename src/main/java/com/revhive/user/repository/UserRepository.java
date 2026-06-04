package com.revhive.user.repository;

import com.revhive.user.dto.request.ChangePasswordRequest;
import com.revhive.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findUserByUserId(Long userId);

    List<User> findTop10ByUsernameContainingIgnoreCase(String query);

}
