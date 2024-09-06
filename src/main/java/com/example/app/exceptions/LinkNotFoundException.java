package com.example.app.exceptions;

import java.util.NoSuchElementException;

public class LinkNotFoundException extends NoSuchElementException {
    public LinkNotFoundException(long id) {
        super("Link with id " + id + " not found.");
    }
    public LinkNotFoundException(String URL) {
        super("Invalid URL " + URL + " not found.");
    }
}