package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.UserService;
import com.example.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

            userService.deleteUser(id);
            return ResponseEntity.noContent().build();

    }
}
