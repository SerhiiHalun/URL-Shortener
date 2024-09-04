package com.example.app.repository;

import com.example.app.model.User;
import com.example.app.model.dto.SignupRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(SignupRequest signupRequest);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
