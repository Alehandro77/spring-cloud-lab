package com.example.frontend.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AdminClient {

    private final ApiClient api;

    public AdminClient(ApiClient api) { this.api = api; }

    public List<Map<String, Object>> getAllUsers(String token) {
        return api.getList("/api/admin/users",
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, token);
    }

    public List<Map<String, Object>> getAllProducts(String token) {
        return api.getList("/api/admin/products",
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, token);
    }

    public void deleteProduct(Long id, String token) {
        api.delete("/api/admin/products/" + id, token);
    }

    public List<Map<String, Object>> getOrdersByBuyer(Long buyerId, String token) {
        return api.getList("/api/admin/orders/buyer/" + buyerId,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, token);
    }

    public Map<String, Object> createProduct(Map<String, Object> body, String token) {
        return api.post("/api/products", body, Map.class, token);
    }

}