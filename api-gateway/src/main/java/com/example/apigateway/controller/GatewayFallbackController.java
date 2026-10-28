package com.example.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class GatewayFallbackController {

    // Используем RequestMethod.POST и GET, чтобы принимать любой запрос
    @RequestMapping(value = "/auth", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> authFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status", 503, "service", "auth-service", "message", "Сервис авторизации временно недоступен."));
    }

    @RequestMapping(value = "/product", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status", 503, "service", "product-service", "message", "Каталог товаров временно недоступен."));
    }

    @RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> orderFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status", 503, "service", "order-service", "message", "Сервис заказов временно недоступен."));
    }
}