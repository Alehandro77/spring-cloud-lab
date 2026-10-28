package com.example.productservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String brand,
        String category,
        String gender,
        String color,
        List<String> sizes,
        Integer stockQuantity,
        String imageUrl,
        Boolean isActive
) {}