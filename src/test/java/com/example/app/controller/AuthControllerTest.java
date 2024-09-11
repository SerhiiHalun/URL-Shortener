package com.example.app.controller;

import com.example.app.model.Role;
import com.example.app.model.User;
import com.example.app.model.dto.AuthRequest;
import com.example.app.model.dto.SignupRequest;
import com.example.app.service.JwtUtil;
import com.example.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        SignupRequest signupRequest = new SignupRequest("John","password");
        String correctResponse = "User with username ' " + "John" + " ' created!";

        when(userService.registerUser(signupRequest)).thenReturn(correctResponse);

        String actualUser = authController.signup(signupRequest);

        assertEquals(correctResponse, actualUser);
        verify(userService).registerUser(signupRequest);
    }

    @Test
    public void testCreateAuthenticationToken() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        UserDetails userDetails = mock(UserDetails.class);
        String expectedToken = "someToken";

        when(userDetailsService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(expectedToken);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String actualToken = authController.createAuthenticationToken(authRequest);

        assertEquals(expectedToken, actualToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(authRequest.getUsername());
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    public void testCreateAuthenticationToken_InvalidCredentials() {
        AuthRequest authRequest = new AuthRequest("username", "wrongPassword");

        doThrow(new RuntimeException("Authentication Failed"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(RuntimeException.class, () -> authController.createAuthenticationToken(authRequest));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}