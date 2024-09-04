package com.example.app.service;

import com.example.app.model.Link;
import com.example.app.repository.LinkRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LinkServiceTest {
    @Mock
    LinkRepository linkRepository;
    @InjectMocks
    LinkService linkService ;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {

        Link originLink = new Link();
        originLink.setId(5);
        originLink.setFullUrl("ssssssssssssss");
        originLink.setShortUrl("ss");
        originLink.setCreationDate(LocalDate.now());
        originLink.setStatus(Link.OrderStatus.ACTIVE);

        when(linkRepository.findById(5L)).thenReturn(java.util.Optional.of(originLink));
        linkService.add(originLink);
        Assertions.assertEquals(originLink,linkService.getById(5L));



    }
    @Test
    void add() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }


    @Test
    void getLinkClickStatistics() {
    }
}