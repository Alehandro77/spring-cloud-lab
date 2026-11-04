package com.example.userprofileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/orders/buyer/{buyerId}")
    List<Object> getOrdersByBuyerId(@PathVariable("buyerId") Long buyerId);
}