package com.example.app.controller;

import com.example.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Controller", description = "API для управління користувачами адміністратора")
public class AdminController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    @Operation(summary = "Видалення користувача", description = "Видаляє користувача за його ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

            userService.deleteUser(id);
            return ResponseEntity.noContent().build();

    }
}
