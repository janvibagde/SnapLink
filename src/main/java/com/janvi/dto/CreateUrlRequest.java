package com.janvi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateUrlRequest {
    @NotBlank
    @Pattern(regexp = "^(https?://).+", message = "URL must start with http:// or https://")
    private String originalUrl;

    private String customAlias;       // optional
    private LocalDateTime expiryDate; // optional
}
