package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Категория товаров")
public record CategoryResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "Футболки") String name
) {}