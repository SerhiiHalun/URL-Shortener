package com.example.app.service;

import com.example.app.model.User;
import com.example.app.model.dto.SignupRequest;
import com.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByName(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public void deleteUser(Long id) {
    }

    public User updateUser(User user) {
        return null;
    }

    public User getUserById(Long id) {
        return null;
    }
    @Transactional
    public String createUser(SignupRequest user) {
        return "User created";
    }
}
