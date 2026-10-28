package com.example.productservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        @NotBlank String name,
        String description,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal price,

        String brand,

        @NotNull Long categoryId,

        @NotBlank String gender,         // "MEN" / "WOMEN" / "UNISEX"

        String color,

        @NotEmpty List<String> sizes,    // ["S", "M", "L"]

        @NotNull @Min(0) Integer stockQuantity,

        String imageUrl
) {}