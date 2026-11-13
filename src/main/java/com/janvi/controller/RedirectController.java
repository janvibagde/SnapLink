package com.janvi.controller;

import com.janvi.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String target = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)   // 302
                .location(URI.create(target))
                .build();
    }
}
