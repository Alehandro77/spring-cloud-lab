package com.example.adminservice.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long buyerId,
        String status,
        BigDecimal totalPrice,
        List<OrderItemDto> items,
        LocalDateTime createdAt
) {
    public record OrderItemDto(
            Long productId,
            String productName,
            BigDecimal price,
            String size,
            Integer quantity
    ) {}
}