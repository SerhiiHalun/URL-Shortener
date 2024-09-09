package com.example.app.mapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.app.model.Link;
import com.example.app.model.User;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.LinkService;
import com.example.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class LinkMapperTest {

    @Mock
    private UserService userService;

    @Mock
    private LinkService linkService;

    @InjectMocks
    private LinkMapper linkMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLinkCreateDTOToEntity() {
        // Arrange
        LinkCreateDTO linkDTO = new LinkCreateDTO();
        linkDTO.setFullUrl("https://www.youtube.com");
        linkDTO.setUserName("testUser");

        User user = new User();
        user.setUsername("testUser");

        when(linkService.generateShortUrl()).thenReturn("shortUrl");
        when(userService.findByName("testUser")).thenReturn(user);


        Link link = linkMapper.linkCreateDTOToEntity(linkDTO);

        assertNotNull(link);
        assertEquals(LocalDate.now(), link.getCreationDate());
        assertEquals("https://www.youtube.com", link.getFullUrl());
        assertEquals("shortUrl", link.getShortUrl());
        assertEquals(0, link.getTransitionCounter());
        assertEquals(Link.OrderStatus.ACTIVE, link.getStatus());
        assertEquals(user, link.getUser());

        verify(linkService).generateShortUrl();
        verify(userService).findByName("testUser");
    }
}
