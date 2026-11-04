package com.example.reviewservice.controller;

import com.example.reviewservice.dto.CreateReviewRequest;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.repository.ReviewRepository;
import com.example.reviewservice.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class UserReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public UserReviewController(ReviewRepository reviewRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }

    @GetMapping("/count/{userId}")
    public Integer getCountByUser(@PathVariable Long userId) {
        return reviewRepository.countByUserId(userId);
    }
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody CreateReviewRequest request) {
        try {
            Review saved = reviewService.createReview(request);
            return ResponseEntity.ok(Map.of("message", "Отзыв добавлен", "id", saved.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}