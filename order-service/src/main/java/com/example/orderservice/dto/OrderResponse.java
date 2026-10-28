package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long buyerId,
        String status,
        BigDecimal totalPrice,
        List<OrderItemResponse> items,
        LocalDateTime createdAt
) {}