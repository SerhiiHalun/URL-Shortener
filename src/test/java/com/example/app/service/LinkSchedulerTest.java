package com.example.app.service;

import com.example.app.model.Link;
import com.example.app.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LinkSchedulerTest {

    @InjectMocks
    private LinkScheduler linkScheduler;

    @Mock
    private LinkRepository linkRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateOrderStatus() {

        Link activeLinkOld = new Link();
        activeLinkOld.setCreationDate(LocalDate.now().minus(31, ChronoUnit.DAYS));
        activeLinkOld.setStatus(Link.OrderStatus.ACTIVE);

        Link activeLinkRecent = new Link();
        activeLinkRecent.setCreationDate(LocalDate.now().minus(5, ChronoUnit.DAYS));
        activeLinkRecent.setStatus(Link.OrderStatus.ACTIVE);

        List<Link> activeLinks = Arrays.asList(activeLinkOld, activeLinkRecent);


        when(linkRepository.findAllByStatus(Link.OrderStatus.ACTIVE)).thenReturn(activeLinks);


        linkScheduler.updateOrderStatus();


        verify(linkRepository, times(1)).save(activeLinkOld);
        assertEquals(Link.OrderStatus.DISABLE, activeLinkOld.getStatus());


        verify(linkRepository, never()).save(activeLinkRecent);
    }
}
