package com.example.app.service;

import com.example.app.exceptions.LinkDisabledException;
import com.example.app.exceptions.LinkNotFoundException;
import com.example.app.mapper.LinkMapper;
import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    @InjectMocks
    private LinkService linkService;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private LinkMapper linkMapper;

    @Mock
    private LinkUtil linkUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLink() {

        String tokenJWT = "Bearer testToken";
        LinkCreateDTO dto = new LinkCreateDTO();
        dto.setFullUrl("https://www.youtube.com");

        Link link = new Link();
        link.setShortUrl("abc123");

        when(linkMapper.linkCreateDTOToEntity(anyString(), any(LinkCreateDTO.class))).thenReturn(link);
        when(linkRepository.save(any(Link.class))).thenReturn(link);

        String shortUrl = linkService.add(tokenJWT, dto);

        assertNotNull(shortUrl);
        assertEquals("abc123", shortUrl);
        verify(linkRepository, times(1)).save(link);
        verify(linkMapper, times(1)).linkCreateDTOToEntity(anyString(), any(LinkCreateDTO.class));

    }


    @Test
    void testGetFullUrlLinkNotFound() {
        when(linkRepository.findByShortUrl(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> linkService.getFullUrl("nonExistentUrl"));
    }

    @Test
    void testGetFullUrlLinkDisabled() {
        Link link = new Link();
        link.setStatus(Link.OrderStatus.DISABLE);

        when(linkRepository.findByShortUrl(anyString())).thenReturn(Optional.of(link));

        assertThrows(LinkDisabledException.class, () -> linkService.getFullUrl("abc123"));
    }

    @Test
    void testGetFullUrlSuccess() {
        Link link = new Link();
        link.setShortUrl("abc123");
        link.setFullUrl("https://www.youtube.com");
        link.setTransitionCounter(0);
        link.setStatus(Link.OrderStatus.ACTIVE);

        when(linkRepository.findByShortUrl(anyString())).thenReturn(Optional.of(link));

        String fullUrl = linkService.getFullUrl("abc123");

        assertEquals("https://www.youtube.com", fullUrl);
        assertEquals(1, link.getTransitionCounter());
        verify(linkRepository, times(1)).save(link);
    }

    @Test
    void testDeleteLinkById() {
        when(linkRepository.existsById(anyLong())).thenReturn(true);

        boolean result = linkService.deleteById(1L);

        assertTrue(result);
        verify(linkRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLinkByIdNotFound() {
        when(linkRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(LinkNotFoundException.class, () -> linkService.deleteById(1L));
    }

    @Test
    void testExtendLinkValidity() {
        Link link = new Link();
        link.setStatus(Link.OrderStatus.DISABLE);
        link.setCreationDate(LocalDate.now().minusDays(40));

        when(linkRepository.findById(anyLong())).thenReturn(Optional.of(link));

        String result = linkService.extendLinkValidity(1L);

        assertEquals("Link has been successfully extended", result);
        assertEquals(LocalDate.now(), link.getCreationDate());
        assertEquals(Link.OrderStatus.ACTIVE, link.getStatus());
        verify(linkRepository, times(1)).save(link);
    }

    @Test
    void testCheckUrlExistsSuccess() {
        boolean result = linkService.checkUrlExists("https://www.youtube.com");
        assertTrue(result);
    }

    @Test
    void testCheckUrlExistsInvalid() {
        boolean result = linkService.checkUrlExists("invalid-url");
        assertFalse(result);
    }
    @Test
    void testGetLinkClickStatistics() {

        Link link = new Link();
        link.setTransitionCounter(5);

        when(linkRepository.findById(anyLong())).thenReturn(Optional.of(link));


        int clickStats = linkService.getLinkClickStatistics(1L);


        assertEquals(5, clickStats);
        verify(linkRepository, times(1)).findById(1L);
    }

    @Test
    void testGetShortLink() {

        Link link = new Link();
        link.setShortUrl("abc123");

        when(linkRepository.findById(anyLong())).thenReturn(Optional.of(link));

        String shortUrl = linkService.getShortLink(1L);

        assertEquals("abc123", shortUrl);
        verify(linkRepository, times(1)).findById(1L);
    }



    @Test
    void testValidateLink_ValidLink() {
        Link link = new Link();
        link.setFullUrl("https://example.com");

        assertDoesNotThrow(() -> linkService.validateLink(link));
    }

    @Test
    void testValidateLink_NullLink() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            linkService.validateLink(null);
        });

        assertEquals("Link must not be null.", exception.getMessage());
    }

    @Test
    void testValidateLink_EmptyFullUrl() {
        Link link = new Link();
        link.setFullUrl("");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            linkService.validateLink(link);
        });

        assertEquals("Full URL must not be null or empty.", exception.getMessage());


    }
}
