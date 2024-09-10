package com.example.app.service;

import com.example.app.exceptions.LinkDisabledException;
import com.example.app.exceptions.LinkNotFoundException;
import com.example.app.mapper.LinkMapper;
import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;
    private final LinkMapper linkMapper;

    @Transactional
    public String add(LinkCreateDTO linkCreateDTO)  {
        if(!checkUrlExists(linkCreateDTO.getFullUrl())){
            throw new LinkNotFoundException(linkCreateDTO.getFullUrl());
        }

        Link link = linkMapper.linkCreateDTOToEntity(linkCreateDTO);
        repository.save(link);
        return link.getShortUrl();
    }

    @Transactional
    public boolean deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new LinkNotFoundException(id);
        }
        repository.deleteById(id);
        return true;
    }



    @Transactional(readOnly = true)
    public Link findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public int getLinkClickStatistics(long id) {
        return findById(id).getTransitionCounter();
    }


    @Transactional(readOnly = true)
    public String getShortLink(long id) {
        return findById(id).getShortUrl();
    }

    @Transactional
    public String getFullUrl(String shortUrl) {
        Link link = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("Short URL " + shortUrl + " not found."));

        if (link.getStatus() == Link.OrderStatus.DISABLE) {
            throw new LinkDisabledException("The link with short URL " + shortUrl + " is disabled.");
        }

        link.setTransitionCounter(link.getTransitionCounter() + 1);
        repository.save(link);

        return link.getFullUrl();
    }
    @Transactional
    public String extendLinkValidity(long linkId) {
        Link link = findById(linkId);
        link.setCreationDate(LocalDate.now());
        link.setStatus(Link.OrderStatus.ACTIVE);

        repository.save(link);
        return "Link has been successfully extended";
    }


    public void validateLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link must not be null.");
        }
        if (link.getFullUrl()== null || link.getFullUrl().isEmpty()) {
            throw new IllegalArgumentException("Full URL must not be null or empty.");
        }
    }

    public boolean checkUrlExists(String fullUrl) {
        try {
            if (fullUrl == null || fullUrl.isEmpty()) {
                throw new IllegalArgumentException("Full URL must not be null or empty.");
            }
            URL url = new URL(fullUrl);
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


}
