package com.example.app.service;

import com.example.app.model.Link;
import com.example.app.model.User;
import com.example.app.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;
    private final UserService userService;

    @Transactional
    public String add(String userName, String fullUrl)  {
        if(checkUrlExists(fullUrl)){
            throw new LinkNotFoundException(fullUrl);
        }
        String shorturl = generateShortUrl();
        Link link = Link.builder()
                .creationDate(LocalDate.now())
                .fullUrl(fullUrl)
                .shortUrl(shorturl)
                .transitionCounter(0)
                .status(Link.OrderStatus.ACTIVE)
                .user(userService.findByName(userName))
                .build();
        validateLink(link);
        repository.save(link);
        return shorturl;
    }

    @Transactional
    public boolean deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new LinkNotFoundException(id);
        }
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean update(Link link) {
        validateLink(link);
        if (!repository.existsById(link.getId())) {
            throw new LinkNotFoundException(link.getId());
        }
        repository.save(link);
        return true;
    }

    @Transactional(readOnly = true)
    public Link getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public int getLinkClickStatistics(long id) {
        return getById(id).getTransitionCounter();
    }



    @Transactional(readOnly = true)
    public String getShortLink(long id) {
        return getById(id).getShortUrl();
    }

    private void validateLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link must not be null.");
        }
        if (link.getFullUrl() == null || link.getFullUrl().isEmpty()) {
            throw new IllegalArgumentException("Full URL must not be null or empty.");
        }
    }

    private boolean checkUrlExists(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();


            return (responseCode >= 200 && responseCode < 400);

        } catch (IOException e) {

            return false;
        }
    }
    @Transactional
    public String getFullUrl(String shortUrl) {
        Link link = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("Short URL " + shortUrl + " not found."));
        link.setTransitionCounter(link.getTransitionCounter() + 1);

        repository.save(link);

        return link.getFullUrl();
    }

    public String generateShortUrl(){
        return  "https://shorturl/" + UUID.randomUUID().toString().substring(0, 8);
    }
    public static class LinkNotFoundException extends NoSuchElementException {
        public LinkNotFoundException(long id) {
            super("Link with id " + id + " not found.");
        }
        public LinkNotFoundException(String URL) {
            super("Invalid URL " + URL + " not found.");
        }
    }

}
