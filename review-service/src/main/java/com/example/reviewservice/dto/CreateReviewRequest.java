package com.example.reviewservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на создание отзыва")
public record CreateReviewRequest(
        @Schema(example = "1") @NotNull Long productId,
        @Schema(example = "1") @NotNull Long userId,
        @Schema(example = "5", minimum = "1", maximum = "5") @NotNull @Min(1) @Max(5) Integer rating,
        @Schema(example = "Отличная футболка!") String comment
) {}