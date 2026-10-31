package com.example.adminservice.client;

import com.example.adminservice.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", contextId = "adminProductClient")
public interface ProductClient {

    @GetMapping("/products")
    List<ProductDto> getAllProducts();

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable("id") Long id);
}