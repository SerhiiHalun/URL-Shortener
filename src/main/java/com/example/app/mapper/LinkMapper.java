package com.example.app.mapper;


import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.LinkService;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LinkMapper {
    private final UserService userService;
    private final LinkService linkService;
    public Link linkCreateDTOToEntity(LinkCreateDTO linkDTO){


        String shorturl = linkService.generateShortUrl();

        Link link = Link.builder()
                .creationDate(LocalDate.now())
                .fullUrl(linkDTO.getFullUrl())
                .shortUrl(shorturl)
                .transitionCounter(0)
                .status(Link.OrderStatus.ACTIVE)
                .user(userService.findByName(linkDTO.getUserName()))
                .build();
        return link;
    }

}
