package com.example.app.exceptions;


public class LinkDisabledException extends RuntimeException {
    public LinkDisabledException(String url) {
        super("The link with short URL " + url + " is disabled.");
    }
}