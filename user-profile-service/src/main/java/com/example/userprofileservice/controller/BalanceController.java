package com.example.userprofileservice.controller;

import com.example.userprofileservice.dto.BalanceOperationRequest;
import com.example.userprofileservice.dto.BalanceResponse;
import com.example.userprofileservice.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/balance")
@Tag(name = "Balance", description = "Управление балансом")
public class BalanceController {

    private final ProfileService profileService;

    public BalanceController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Пополнить баланс (ADMIN)")
    @PostMapping("/deposit/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BalanceResponse> deposit(@PathVariable Long userId,
                                                   @RequestBody BalanceOperationRequest req) {
        var profile = profileService.depositBalance(userId, req.amount());
        return ResponseEntity.ok(new BalanceResponse("Баланс пополнен", profile.getBalance()));
    }

    @Operation(summary = "Списать с баланса (ADMIN)")
    @PostMapping("/deduct/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BalanceResponse> deduct(@PathVariable Long userId,
                                                  @RequestBody BalanceOperationRequest req) {
        var profile = profileService.deductBalance(userId, req.amount());
        return ResponseEntity.ok(new BalanceResponse("Средства списаны", profile.getBalance()));
    }
}