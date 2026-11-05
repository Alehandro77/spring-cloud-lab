package com.example.frontend.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderClient {

    private final ApiClient api;

    public OrderClient(ApiClient api) { this.api = api; }

    public Map<String, Object> getCart(Long userId, String token) {
        return api.get("/api/cart/" + userId, Map.class, token);
    }

    public Map<String, Object> addToCart(Long userId, Long productId, Integer quantity, String token) {
        return api.post("/api/cart/" + userId + "/items",
                Map.of("productId", productId, "quantity", quantity),
                Map.class, token);
    }

    public Map<String, Object> updateCartItem(Long userId, Long itemId, Integer quantity, String token) {
        return api.put("/api/cart/" + userId + "/items/" + itemId,
                Map.of("quantity", quantity),
                Map.class, token);
    }

    public Map<String, Object> removeCartItem(Long userId, Long itemId, String token) {
        return api.delete_return("/api/cart/" + userId + "/items/" + itemId, Map.class, token);
    }

    public void clearCart(Long userId, String token) {
        api.delete("/api/cart/" + userId, token);
    }

    public Map<String, Object> createOrder(Long userId, String token) {
        return api.post("/api/orders/buyer/" + userId, null, Map.class, token);
    }

    public List<Map<String, Object>> getMyOrders(Long userId, String token) {
        return api.getList("/api/orders/buyer/" + userId,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}, token);
    }

    public Map<String, Object> getOrder(Long orderId, String token) {
        return api.get("/api/orders/" + orderId, Map.class, token);
    }
}