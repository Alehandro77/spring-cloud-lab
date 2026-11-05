package com.example.reviewservice.controller;

import com.example.reviewservice.dto.ReviewResponse;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.repository.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews (public)", description = "Публичный просмотр отзывов")
public class PublicReviewController {

    private final ReviewRepository reviewRepository;

    public PublicReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Operation(summary = "Отзывы на товар")
    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getByProduct(@PathVariable Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse toResponse(Review r) {
        return new ReviewResponse(
                r.getId(),
                r.getProductId(),
                r.getUserId(),
                r.getRating(),
                r.getComment(),
                r.getCreatedAt()
        );
    }
}