package com.example.userprofileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Операция с балансом")
public record BalanceOperationRequest(
        @Schema(description = "Сумма", example = "1000.00") BigDecimal amount
) {}