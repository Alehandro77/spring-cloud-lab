package com.example.reviewservice.controller;

import com.example.reviewservice.dto.CreateReviewRequest;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.repository.ReviewRepository;
import com.example.reviewservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews (user)", description = "Создание отзывов")
public class UserReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public UserReviewController(ReviewRepository reviewRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }

    @Operation(summary = "Кол-во отзывов пользователя")
    @GetMapping("/count/{userId}")
    public Integer getCountByUser(@PathVariable Long userId) {
        return reviewRepository.countByUserId(userId);
    }

    @Operation(summary = "Оставить отзыв (нужен JWT)")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createReview(@Valid @RequestBody CreateReviewRequest request) {
        try {
            Review saved = reviewService.createReview(request);
            return ResponseEntity.ok(Map.of("message", "Отзыв добавлен", "id", saved.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}