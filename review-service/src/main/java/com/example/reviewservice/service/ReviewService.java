package com.example.reviewservice.service;

import com.example.reviewservice.client.OrderClient;
import com.example.reviewservice.dto.CreateReviewRequest;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderClient orderClient;

    public ReviewService(ReviewRepository reviewRepository, OrderClient orderClient) {
        this.reviewRepository = reviewRepository;
        this.orderClient = orderClient;
    }

    public Review createReview(CreateReviewRequest request) {
        log.info("Creating review: productId={}, userId={}, rating={}",
                request.productId(), request.userId(), request.rating());

        // Проверка: покупал ли пользователь этот товар
        boolean bought = false;
        try {
            List<Map<String, Object>> orders = orderClient.getOrdersByBuyer(request.userId());
            log.info("Orders fetched: {}", orders != null ? orders.size() : 0);

            if (orders != null) {
                for (Map<String, Object> order : orders) {
                    Object items = order.get("items");
                    if (items instanceof List<?> itemList) {
                        for (Object item : itemList) {
                            if (item instanceof Map<?, ?> itemMap) {
                                Object pid = itemMap.get("productId");
                                if (pid != null && toLong(pid) == request.productId().longValue()) {
                                    bought = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (bought) break;
                }
            }
        } catch (Exception e) {
            log.error("Failed to check orders: {}", e.getMessage());
            throw new RuntimeException("Сервис заказов недоступен, попробуйте позже");
        }

        if (!bought) {
            log.warn("Product not bought: userId={}, productId={}", request.userId(), request.productId());
            throw new RuntimeException("Товар не куплен — оставить отзыв нельзя");
        }

        Review review = new Review();
        review.setProductId(request.productId());
        review.setUserId(request.userId());
        review.setRating(request.rating());
        review.setComment(request.comment());

        Review saved = reviewRepository.save(review);
        log.info("Review created: id={}", saved.getId());
        return saved;
    }

    private long toLong(Object obj) {
        if (obj instanceof Number n) return n.longValue();
        return Long.parseLong(obj.toString());
    }
}