package com.example.app.service;

import com.example.app.model.Link;
import com.example.app.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkScheduler {
    private final LinkRepository linkRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOrderStatus() {
        List<Link> activeLinks = linkRepository.findAllByStatus(Link.OrderStatus.ACTIVE);

        for (Link link : activeLinks) {
            if (link.getCreationDate().plus(30, ChronoUnit.DAYS).isBefore(LocalDate.now())) {
                link.setStatus(Link.OrderStatus.DISABLE);
                linkRepository.save(link);
            }
        }
    }
}
