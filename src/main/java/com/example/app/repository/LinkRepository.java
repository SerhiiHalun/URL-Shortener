package com.example.app.repository;

import com.example.app.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<Link> findByShortUrl(String shortUrl);
    List<Link> findAllByStatus(Link.OrderStatus status);
    List<Link> findLinksByUserId(Long userId);
}
