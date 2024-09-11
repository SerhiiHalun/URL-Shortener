package com.example.app.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LinkUtil {

    public String generateShortUrl(){
        return  UUID.randomUUID().toString().substring(0, 8);
    }

}
