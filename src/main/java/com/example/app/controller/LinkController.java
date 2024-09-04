package com.example.app.controller;

import com.example.app.model.Link;
import com.example.app.model.User;
import com.example.app.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/link")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;
    @GetMapping("/{userName},{fullUrl}")
    public String createShortUrl(@PathVariable String userName, @PathVariable String fullUrl){
        return linkService.add(userName,fullUrl);
    }
    @GetMapping("/{id}")
    public Link getLinkById(@PathVariable long id) {
        return linkService.getById(id);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToFullUrl(@PathVariable String shortUrl) {
        String fullUrl = linkService.getFullUrl(shortUrl);
        return new RedirectView(fullUrl);
    }
}
