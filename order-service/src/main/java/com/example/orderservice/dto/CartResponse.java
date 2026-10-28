package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Long id,
        Long buyerId,
        List<CartItemResponse> items,
        BigDecimal total
) {
    public record CartItemResponse(
            Long id, Long productId, String title,
            BigDecimal price, Integer quantity
    ) {}
}