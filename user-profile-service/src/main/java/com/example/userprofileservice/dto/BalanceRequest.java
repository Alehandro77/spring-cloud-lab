package com.example.userprofileservice.dto;

import java.math.BigDecimal;

public class BalanceRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}