package com.example.userprofileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Ответ после операции с балансом")
public record BalanceResponse(
        @Schema(example = "Баланс пополнен") String message,
        @Schema(example = "1000.00") BigDecimal newBalance
) {}