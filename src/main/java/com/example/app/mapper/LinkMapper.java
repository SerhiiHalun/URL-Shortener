package com.example.app.mapper;


import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;

import com.example.app.service.LinkUtil;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LinkMapper {
    private final UserService userService;
    private final LinkUtil linkUtil;
    public Link LinkCreateDTOToEntity(LinkCreateDTO linkDTO){

        String shorturl = linkUtil.generateShortUrl();

        return Link.builder()
                .creationDate(LocalDate.now())
                .fullUrl(linkDTO.getFullUrl())
                .shortUrl(shorturl)
                .transitionCounter(0)
                .status(Link.OrderStatus.ACTIVE)
                .user(userService.findByName(linkDTO.getUserName()))
                .build();
    }

}
