package com.example.app.service;

import com.example.app.exceptions.UserNotFoundException;
import com.example.app.exceptions.ValidationException;
import com.example.app.model.User;
import com.example.app.model.dto.SignupRequest;
import com.example.app.repository.RoleRepository;
import com.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public String registerUser(SignupRequest newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new ValidationException("Username already exists.");
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        roleRepository.findByName("ROLE_USER").ifPresent(user::addRole);
        userRepository.save(user);
        return "User with username ' " + user.getUsername() + " ' created!";
    }

    @Cacheable("users")
    public User findByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }


    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
}
