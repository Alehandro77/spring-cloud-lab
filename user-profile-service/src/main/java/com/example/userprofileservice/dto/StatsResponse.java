package com.example.userprofileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Статистика пользователя")
public record StatsResponse(
        @Schema(example = "1000.00") BigDecimal balance,
        @Schema(example = "5") Integer orderCount,
        @Schema(example = "2") Integer reviewCount
) {}