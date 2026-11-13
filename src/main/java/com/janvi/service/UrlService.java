package com.janvi.service;

import com.janvi.dto.*;
import com.janvi.entity.Url;
import com.janvi.exception.UrlExpiredException;
import com.janvi.exception.UrlNotFoundException;
import com.janvi.repository.UrlRepository;
import com.janvi.util.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UrlService {

    private static final Set<String> RESERVED = Set.of("api", "admin", "swagger-ui", "error");

    private final UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    // ---------- 1. Short URL banana ----------
    @Transactional
    public CreateUrlResponse createShortUrl(CreateUrlRequest req) {
        Url url = new Url();
        url.setOriginalUrl(req.getOriginalUrl());
        url.setExpiryDate(req.getExpiryDate());

        String alias = req.getCustomAlias();
        if (alias != null && !alias.isBlank()) {
            if (RESERVED.contains(alias) || urlRepository.existsByShortCode(alias)) {
                throw new IllegalArgumentException("Alias not available");
            }
            url.setShortCode(alias);
            url = urlRepository.save(url);
        } else {
            url = urlRepository.save(url);                          // id generate hoti hai
            url.setShortCode(Base62.encode(url.getId() + 100000));  // id -> code
            url = urlRepository.save(url);
        }

        return new CreateUrlResponse(baseUrl + "/" + url.getShortCode(),
                url.getShortCode(), url.getOriginalUrl());
    }

    // ---------- 2. Redirect ke liye original URL nikalna ----------
    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));

        boolean expired = url.getExpiryDate() != null
                && url.getExpiryDate().isBefore(LocalDateTime.now());

        if (!url.isActive() || expired) {
            throw new UrlExpiredException(shortCode);
        }
        return url.getOriginalUrl();
    }
}