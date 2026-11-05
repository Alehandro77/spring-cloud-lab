package com.example.frontend.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProfileClient {

    private final ApiClient api;

    public ProfileClient(ApiClient api) { this.api = api; }

    public Map<String, Object> getProfile(Long userId, String token) {
        return api.get("/api/profile/" + userId, Map.class, token);
    }

    public Map<String, Object> updateProfile(Long userId, Map<String, Object> body, String token) {
        return api.put("/api/profile/" + userId, body, Map.class, token);
    }

    public Map<String, Object> getStats(Long userId, String token) {
        return api.get("/api/profile/stats/" + userId, Map.class, token);
    }
}