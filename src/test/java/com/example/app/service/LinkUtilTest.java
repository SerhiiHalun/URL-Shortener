package com.example.app.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkUtilTest {

    private final LinkUtil linkUtil = new LinkUtil();

    @Test
    void testGenerateShortUrl() {

        String shortUrl = linkUtil.generateShortUrl();

        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("https://shorturl/"));
        assertEquals(25, shortUrl.length());
    }
}
