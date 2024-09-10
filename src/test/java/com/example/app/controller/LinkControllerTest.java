package com.example.app.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.LinkService;
import com.example.app.service.LinkUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.view.RedirectView;

public class LinkControllerTest {

    @Mock
    private LinkService linkService;

    @InjectMocks
    private LinkController linkController;

    @Mock
    private LinkUtil linkUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShortUrl() {
        LinkCreateDTO linkCreateDTO = new LinkCreateDTO();
        linkCreateDTO.setFullUrl("https://www.youtube.com");
        linkCreateDTO.setUserName("testUser");

        when(linkService.add(linkCreateDTO)).thenReturn("shortUrl");

        String result = linkController.createShortUrl(linkCreateDTO);

        assertEquals("shortUrl", result);
        verify(linkService).add(linkCreateDTO);
    }

    @Test
    public void testGetLinkById() {
        Link link = new Link();
        link.setId(1L);
        link.setFullUrl("https://www.youtube.com");

        when(linkService.findById(1L)).thenReturn(link);

        Link result = linkController.getLinkById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("https://www.youtube.com", result.getFullUrl());
        verify(linkService).findById(1L);
    }

    @Test
    public void testRedirectToFullUrl() {
        String shortUrl = linkUtil.generateShortUrl();
        String fullUrl = "https://www.youtube.com/";

        when(linkService.getFullUrl(shortUrl)).thenReturn(fullUrl);

        RedirectView result = linkController.redirectToFullUrl(shortUrl);

        assertNotNull(result);
        assertEquals(fullUrl, result.getUrl());
        verify(linkService).getFullUrl(shortUrl);
    }

    @Test
    public void testExtendLinkValidity() {
        long id = 1L;

        String result = linkController.extendLinkValidity(id);

        assertEquals("Link validity extended successfully", result);
        verify(linkService).extendLinkValidity(id);
    }
}
