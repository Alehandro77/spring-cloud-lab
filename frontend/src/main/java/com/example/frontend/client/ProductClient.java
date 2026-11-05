package com.example.frontend.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductClient {

    private final ApiClient api;

    public ProductClient(ApiClient api) { this.api = api; }

    public List<Map<String, Object>> getAll() {
        return api.getList("/api/products",
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, null);
    }

    public Map<String, Object> getById(Long id, String token) {
        return api.get("/api/products/" + id, Map.class, token);
    }

    public List<Map<String, Object>> getCategories() {
        return api.getList("/api/categories",
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, null);
    }

    public Map<String, Object> create(Map<String, Object> body, String token) {
        return api.post("/api/products", body, Map.class, token);
    }

    public Map<String, Object> update(Long id, Map<String, Object> body, String token) {
        return api.put("/api/products/" + id, body, Map.class, token);
    }

    public void delete(Long id, String token) {
        api.delete("/api/products/" + id, token);
    }
}