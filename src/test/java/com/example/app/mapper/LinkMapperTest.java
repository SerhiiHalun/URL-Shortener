package com.example.app.mapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.app.model.Link;
import com.example.app.model.User;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.JwtUtil;
import com.example.app.service.LinkService;
import com.example.app.service.LinkUtil;
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
    private JwtUtil jwtUtil;

    @Mock
    private LinkUtil linkUtil;

    @InjectMocks
    private LinkMapper linkMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLinkCreateDTOToEntity() {
        String token = "Bearer testToken";
        String username = "testUser";
        String fullUrl = "https://www.youtube.com";
        String shortUrl = "shortUrl";


        LinkCreateDTO linkDTO = new LinkCreateDTO();
        linkDTO.setFullUrl(fullUrl);

        User user = new User();
        user.setUsername(username);

        when(linkUtil.generateShortUrl()).thenReturn(shortUrl);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userService.findByName(username)).thenReturn(user);


        Link link = linkMapper.linkCreateDTOToEntity(token, linkDTO);


        assertNotNull(link);
        assertEquals(LocalDate.now(), link.getCreationDate());
        assertEquals(fullUrl, link.getFullUrl());
        assertEquals(shortUrl, link.getShortUrl());
        assertEquals(0, link.getTransitionCounter());
        assertEquals(Link.OrderStatus.ACTIVE, link.getStatus());
        assertEquals(user, link.getUser());


        verify(linkUtil).generateShortUrl();
        verify(jwtUtil).extractUsername(token);
        verify(userService).findByName(username);
    }
}
