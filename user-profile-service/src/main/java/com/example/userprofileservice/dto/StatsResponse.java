package com.example.userprofileservice.dto;

import java.math.BigDecimal;

public class StatsResponse {
    private BigDecimal balance;
    private Integer orderCount;
    private Integer reviewCount;

    public StatsResponse(BigDecimal balance, Integer orderCount, Integer reviewCount) {
        this.balance = balance;
        this.orderCount = orderCount;
        this.reviewCount = reviewCount;
    }

    public BigDecimal getBalance() { return balance; }
    public Integer getOrderCount() { return orderCount; }
    public Integer getReviewCount() { return reviewCount; }
}