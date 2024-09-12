package com.example.app.mapper;


import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;

import com.example.app.service.JwtUtil;
import com.example.app.service.LinkUtil;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LinkMapper {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final LinkUtil linkUtil;

    public Link linkCreateDTOToEntity(String token,LinkCreateDTO linkCreateDTO){


        String shorturl = linkUtil.generateShortUrl();

        return Link.builder()
                .creationDate(LocalDate.now())
                .fullUrl(linkCreateDTO.getFullUrl())
                .shortUrl(shorturl)
                .transitionCounter(0)
                .status(Link.OrderStatus.ACTIVE)
                .user(userService.findByName(jwtUtil.extractUsername(token)))
                .build();
    }

}
