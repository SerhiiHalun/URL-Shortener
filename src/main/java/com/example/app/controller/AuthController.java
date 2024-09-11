package com.example.app.controller;

import com.example.app.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.app.model.dto.AuthRequest;
import com.example.app.model.dto.SignupRequest;
import com.example.app.service.JwtUtil;
import com.example.app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "API для реєстрації та автентифікації користувачів")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private  final JwtUtil jwtUtil;


    @PostMapping("/signup")
    @Operation(summary = "Реєстрація нового користувача", description = "Створює нового користувача на основі введених даних реєстрації")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Користувач успішно зареєстрований",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Невірний запит або помилка валідації",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Користувач з таким логіном вже існує",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    public String signup(@RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/auth")
    @Operation(summary = "Аутентифікація користувача", description = "Перевіряє логін і пароль користувача та генерує JWT токен для подальшої аутентифікації")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT токен успішно згенерований"),
            @ApiResponse(responseCode = "403", description = "Невірний логін або пароль")
    })
    public String createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }
}
