package com.example.userprofileservice.controller;

import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        UserProfile profile = profileService.getOrCreateProfile(userId);
        return ResponseEntity.ok(profile);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        try {
            Integer age = request.get("age") != null ? Integer.parseInt(request.get("age")) : null;
            
            UserProfile updated = profileService.updateProfile(
                userId,
                request.get("firstName"),
                request.get("lastName"),
                age,
                request.get("address")
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}