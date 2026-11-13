package com.janvi.controller;

import com.janvi.dto.*;
import com.janvi.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<CreateUrlResponse> create(@Valid @RequestBody CreateUrlRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.createShortUrl(req));
    }
}