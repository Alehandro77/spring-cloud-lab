package com.example.reviewservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Отзыв на товар")
public record ReviewResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long productId,
        @Schema(example = "1") Long userId,
        @Schema(example = "5") Integer rating,
        @Schema(example = "Отличная футболка!") String comment,
        LocalDateTime createdAt
) {}