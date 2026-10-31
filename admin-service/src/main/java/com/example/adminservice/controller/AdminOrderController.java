package com.example.adminservice.controller;

import com.example.adminservice.client.OrderClient;
import com.example.adminservice.client.dto.OrderDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@Tag(name = "Admin: Orders", description = "Управление заказами")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderClient orderClient;

    public AdminOrderController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Operation(summary = "Заказы пользователя")
    @GetMapping("/buyer/{buyerId}")
    @CircuitBreaker(name = "OrderClient", fallbackMethod = "ordersFallback")
    public List<OrderDto> getOrdersByBuyer(@PathVariable Long buyerId) {
        return orderClient.getOrdersByBuyer(buyerId);
    }

    public List<OrderDto> ordersFallback(Long buyerId, Throwable t) {
        return List.of();
    }
}