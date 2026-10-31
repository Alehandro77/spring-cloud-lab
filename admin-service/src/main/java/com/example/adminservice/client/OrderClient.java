package com.example.adminservice.client;

import com.example.adminservice.client.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service", contextId = "adminOrderClient")
public interface OrderClient {

    @GetMapping("/orders/buyer/{buyerId}")
    List<OrderDto> getOrdersByBuyer(@PathVariable("buyerId") Long buyerId);
}