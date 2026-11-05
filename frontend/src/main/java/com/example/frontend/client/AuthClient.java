package com.example.frontend.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthClient {

    private final ApiClient api;

    public AuthClient(ApiClient api) { this.api = api; }

    public Map<String, Object> login(String email, String password) {
        return api.post("/api/auth/login",
                Map.of("email", email, "password", password),
                Map.class, null);
    }

    public Map<String, Object> register(String email, String password,
                                        String firstName, String lastName) {
        return api.post("/api/auth/register",
                Map.of("email", email, "password", password,
                        "firstName", firstName, "lastName", lastName),
                Map.class, null);
    }

    public Map<String, Object> me(String token) {
        return api.get("/api/users/me", Map.class, token);
    }
}