package com.example.adminservice.controller;

import com.example.adminservice.client.ProductClient;
import com.example.adminservice.client.dto.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@Tag(name = "Admin: Products", description = "Управление товарами")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductClient productClient;

    public AdminProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Operation(summary = "Все товары")
    @GetMapping
    @CircuitBreaker(name = "ProductClient", fallbackMethod = "productsFallback")
    public List<ProductDto> getAllProducts() {
        return productClient.getAllProducts();
    }

    @Operation(summary = "Удалить товар")
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "ProductClient", fallbackMethod = "deleteFallback")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productClient.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    public List<ProductDto> productsFallback(Throwable t) {
        return List.of();
    }

    public ResponseEntity<Void> deleteFallback(Long id, Throwable t) {
        return ResponseEntity.status(503).build();
    }
}