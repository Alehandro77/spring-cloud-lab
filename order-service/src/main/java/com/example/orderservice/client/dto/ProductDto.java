package com.example.orderservice.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        Boolean isActive,
        List<String> sizes
) {}