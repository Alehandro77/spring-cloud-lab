package com.example.userprofileservice.controller;

import com.example.userprofileservice.client.OrderClient;
import com.example.userprofileservice.client.ReviewClient;
import com.example.userprofileservice.dto.StatsResponse;
import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/stats")
@Tag(name = "Stats", description = "Статистика пользователя")
public class StatsController {

    private final ProfileService profileService;
    private final OrderClient orderClient;
    private final ReviewClient reviewClient;

    public StatsController(ProfileService profileService, OrderClient orderClient, ReviewClient reviewClient) {
        this.profileService = profileService;
        this.orderClient = orderClient;
        this.reviewClient = reviewClient;
    }

    @Operation(summary = "Статистика (баланс, заказы, отзывы)")
    @GetMapping("/{userId}")
    public StatsResponse getStats(@PathVariable Long userId) {
        UserProfile profile = profileService.getOrCreateProfile(userId);

        Integer orderCount = 0;
        try {
            List<Object> orders = orderClient.getOrdersByBuyerId(userId);
            if (orders != null) orderCount = orders.size();
        } catch (Exception e) {
            System.out.println("Order service unavailable: " + e.getMessage());
        }

        Integer reviewCount = 0;
        try {
            Integer count = reviewClient.getReviewCountByUserId(userId);
            if (count != null) reviewCount = count;
        } catch (Exception e) {
            System.out.println("Review service unavailable: " + e.getMessage());
        }

        return new StatsResponse(profile.getBalance(), orderCount, reviewCount);
    }
}