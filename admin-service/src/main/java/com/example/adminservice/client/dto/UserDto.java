package com.example.adminservice.client.dto;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String role,
        Boolean isActive,
        LocalDateTime createdAt
) {}