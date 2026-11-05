package com.example.userprofileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service")
public interface ReviewClient {

    // URL должен совпадать с тем, что в UserReviewController (порт 8087)
    @GetMapping("/reviews/count/{userId}")
    Integer getReviewCountByUserId(@PathVariable("userId") Long userId);
}