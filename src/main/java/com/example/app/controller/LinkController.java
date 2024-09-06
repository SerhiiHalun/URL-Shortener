package com.example.app.controller;

import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/api/v1/link")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;
    @GetMapping("/create")
    public String createShortUrl(@RequestBody LinkCreateDTO linkCreateDTO){
        return linkService.add(linkCreateDTO);
    }
    @GetMapping("/{id}")
    public Link getLinkById(@PathVariable long id) {
        return linkService.findById(id);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToFullUrl(@PathVariable String shortUrl) {
        String fullUrl = linkService.getFullUrl(shortUrl);
        return new RedirectView(fullUrl);
    }
}
