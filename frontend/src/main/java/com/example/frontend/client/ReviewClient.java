package com.example.frontend.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReviewClient {

    private final ApiClient api;

    public ReviewClient(ApiClient api) { this.api = api; }

    public List<Map<String, Object>> getByProduct(Long productId) {
        return api.getList("/api/reviews/product/" + productId,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, null);
    }

    public Integer countByUser(Long userId) {
        return api.get("/api/reviews/count/" + userId, Integer.class, null);
    }

    public Map<String, Object> create(Map<String, Object> body, String token) {
        return api.post("/api/reviews", body, Map.class, token);
    }
}