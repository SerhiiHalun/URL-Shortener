package com.example.app.service;

import com.example.app.exceptions.UserNotFoundException;
import com.example.app.exceptions.ValidationException;
import com.example.app.model.User;
import com.example.app.model.dto.SignupRequest;
import com.example.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRegisterUser_UsernameExists() {

        SignupRequest signupRequest = new SignupRequest("existingUser","password123");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);


        assertThrows(ValidationException.class, () -> userService.registerUser(signupRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindByName_UserExists() {

        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        User foundUser = userService.findByName(username);


        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    void testFindByName_UserNotFound() {

        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


        assertThrows(UserNotFoundException.class, () -> userService.findByName(username));
    }

    @Test
    void testDeleteUser_UserExists() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        userService.deleteUser(userId);


        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserNotFound() {

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, never()).deleteById(userId);
    }
}