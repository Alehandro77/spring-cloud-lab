package com.example.orderservice.client;

import com.example.orderservice.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {
            @Override
            public ProductDto getProduct(Long id) {
                return new ProductDto(
                        id,
                        "UNAVAILABLE",
                        BigDecimal.ZERO,
                        0,
                        false,
                        List.of()
                );
            }
        };
    }
}