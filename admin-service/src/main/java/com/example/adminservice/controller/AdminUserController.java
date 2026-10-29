package com.example.adminservice.controller;

import com.example.adminservice.client.UserClient;
import com.example.adminservice.client.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Admin: Users", description = "Управление пользователями")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserClient userClient;

    public AdminUserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @Operation(summary = "Все пользователи")
    @GetMapping
    @CircuitBreaker(name = "UserClient", fallbackMethod = "usersFallback")
    public List<UserDto> getAllUsers() {
        return userClient.getAllUsers();
    }

    public List<UserDto> usersFallback(Throwable t) {
        return List.of();
    }
}