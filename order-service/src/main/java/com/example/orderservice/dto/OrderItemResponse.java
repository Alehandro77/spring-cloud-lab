package com.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal price,
        String size,
        Integer quantity
) {}