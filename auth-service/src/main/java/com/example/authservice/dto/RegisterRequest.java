package com.example.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для регистрации нового пользователя")
public record RegisterRequest(

        @Schema(description = "Email пользователя", example = "user@test.com")
        @NotBlank @Email
        String email,

        @Schema(description = "Пароль (минимум 6 символов)", example = "password123")
        @NotBlank @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
        String password,

        @Schema(description = "Имя", example = "Ivan")
        @NotBlank
        String firstName,

        @Schema(description = "Фамилия", example = "Ivanov")
        @NotBlank
        String lastName,

        @Schema(description = "Телефон (опционально)", example = "+79001234567")
        String phone
) {}