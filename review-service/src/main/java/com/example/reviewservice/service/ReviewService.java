package com.example.reviewservice.service;

import com.example.reviewservice.dto.CreateReviewRequest;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(CreateReviewRequest request) {
        log.info("Creating review: productId={}, userId={}, rating={}",
                request.productId(), request.userId(), request.rating());

        Review review = new Review();
        review.setProductId(request.productId());
        review.setUserId(request.userId());
        review.setRating(request.rating());
        review.setComment(request.comment());

        Review saved = reviewRepository.save(review);
        log.info("Review created: id={}", saved.getId());
        return saved;
    }
}