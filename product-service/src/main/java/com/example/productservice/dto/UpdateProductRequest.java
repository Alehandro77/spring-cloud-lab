package com.example.productservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        String brand,
        Long categoryId,
        String gender,
        String color,
        List<String> sizes,
        Integer stockQuantity,
        String imageUrl,
        Boolean isActive
) {}