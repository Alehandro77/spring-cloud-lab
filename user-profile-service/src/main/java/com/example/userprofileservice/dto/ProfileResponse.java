package com.example.userprofileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Профиль пользователя")
public record ProfileResponse(
        @Schema(example = "1") Long userId,
        @Schema(example = "Ivan") String firstName,
        @Schema(example = "Ivanov") String lastName,
        @Schema(example = "25") Integer age,
        @Schema(example = "Moscow") String address,
        @Schema(example = "1000.00") BigDecimal balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}