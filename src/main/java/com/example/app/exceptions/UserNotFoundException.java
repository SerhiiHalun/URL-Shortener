package com.example.app.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("User with id " + id + " not found.");
    }

    public UserNotFoundException(String username) {
        super("User with username " + username + " not found.");
    }
}
