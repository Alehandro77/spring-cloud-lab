package com.example.adminservice.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
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