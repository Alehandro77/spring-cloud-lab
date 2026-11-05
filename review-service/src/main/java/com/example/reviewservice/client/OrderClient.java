package com.example.reviewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "order-service", contextId = "reviewOrderClient")
public interface OrderClient {

    @GetMapping("/orders/buyer/{buyerId}")
    List<Map<String, Object>> getOrdersByBuyer(@PathVariable("buyerId") Long buyerId);
}