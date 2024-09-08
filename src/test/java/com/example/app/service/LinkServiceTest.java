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
        //linkService.add(String ,originLink);
        Assertions.assertEquals(originLink,linkService.findById(5L));



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

    @Test
    void testAdd() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void findById() {
    }

    @Test
    void testGetLinkClickStatistics() {
    }

    @Test
    void getShortLink() {
    }

    @Test
    void getFullUrl() {
    }

    @Test
    void extendLinkValidity() {
    }

    @Test
    void generateShortUrl() {
    }

    @Test
    void validateLink() {
    }

    @Test
    void checkUrlExists() {
    }
}