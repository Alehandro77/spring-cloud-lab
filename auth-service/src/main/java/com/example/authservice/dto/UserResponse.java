package com.example.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Данные пользователя (без пароля)")
public record UserResponse(

        @Schema(example = "1")
        Long id,

        @Schema(example = "user@test.com")
        String email,

        @Schema(example = "Ivan")
        String firstName,

        @Schema(example = "Ivanov")
        String lastName,

        @Schema(example = "+79001234567")
        String phone,

        @Schema(example = "USER")
        String role,

        @Schema(example = "true")
        Boolean isActive,

        @Schema(example = "2026-10-28T14:12:37")
        LocalDateTime createdAt
) {}