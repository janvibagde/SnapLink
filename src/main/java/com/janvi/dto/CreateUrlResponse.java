package com.janvi.dto;

import lombok.*;

@Getter @AllArgsConstructor
public class CreateUrlResponse {
    private String shortUrl;
    private String shortCode;
    private String originalUrl;
}
