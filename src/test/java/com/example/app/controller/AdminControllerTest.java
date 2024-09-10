package com.example.app.controller;

import com.example.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteUser_UserExists_ReturnsNoContent() {
        Long userId = 1L;
        ResponseEntity<Void> response = adminController.deleteUser(userId);
        verify(userService, times(1)).deleteUser(userId);
        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void deleteUser_UserDoesNotExist_ReturnsNoContent() {
        Long userId = 2L;
        ResponseEntity<Void> response = adminController.deleteUser(userId);
        verify(userService, times(1)).deleteUser(userId);
        assertEquals(ResponseEntity.noContent().build(), response);
    }
}
