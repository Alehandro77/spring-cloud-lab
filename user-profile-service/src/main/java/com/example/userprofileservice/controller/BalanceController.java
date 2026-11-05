package com.example.userprofileservice.controller;

import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/profile/balance")
public class BalanceController {

    private final ProfileService profileService;

    public BalanceController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @PostMapping("/deposit/{userId}")
    public ResponseEntity<?> deposit(@PathVariable Long userId, @RequestBody Map<String, Object> request) {
        try {
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            UserProfile profile = profileService.depositBalance(userId, amount);
            return ResponseEntity.ok(Map.of(
                "message", "Баланс пополнен", 
                "newBalance", profile.getBalance()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/deduct/{userId}")
    public ResponseEntity<?> deduct(@PathVariable Long userId, @RequestBody Map<String, Object> request) {
        try {
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            UserProfile profile = profileService.deductBalance(userId, amount);
            return ResponseEntity.ok(Map.of(
                "message", "Средства списаны", 
                "newBalance", profile.getBalance()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}