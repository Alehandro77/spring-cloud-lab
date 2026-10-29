package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания категории")
public record CreateCategoryRequest(
        @Schema(description = "Название категории", example = "Аксессуары")
        @NotBlank @Size(max = 100)
        String name
) {}