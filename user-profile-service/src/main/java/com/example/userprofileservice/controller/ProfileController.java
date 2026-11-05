package com.example.userprofileservice.controller;

import com.example.userprofileservice.dto.ProfileResponse;
import com.example.userprofileservice.dto.UpdateProfileRequest;
import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Tag(name = "Profile", description = "Профиль пользователя")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Профиль (get or create)")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(toResponse(profileService.getOrCreateProfile(userId)));
    }

    @Operation(summary = "Обновить профиль")
    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable Long userId,
                                                         @RequestBody UpdateProfileRequest req) {
        UserProfile updated = profileService.updateProfile(
                userId, req.firstName(), req.lastName(), req.age(), req.address());
        return ResponseEntity.ok(toResponse(updated));
    }

    private ProfileResponse toResponse(UserProfile p) {
        return new ProfileResponse(
                p.getUserId(), p.getFirstName(), p.getLastName(),
                p.getAge(), p.getAddress(), p.getBalance(),
                p.getCreatedAt(), p.getUpdatedAt());
    }
}