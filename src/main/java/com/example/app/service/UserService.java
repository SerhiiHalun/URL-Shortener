package com.example.app.service;

import com.example.app.model.User;
import com.example.app.model.dto.SignupRequest;
import com.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequest user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id)
    {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}