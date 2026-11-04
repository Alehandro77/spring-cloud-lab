package com.example.userprofileservice.service;

import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProfileService {

    private final UserProfileRepository repository;

    public ProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public UserProfile getOrCreateProfile(Long userId) {
        return repository.findById(userId).orElseGet(() -> {
            UserProfile profile = new UserProfile();
            profile.setUserId(userId);
            profile.setFirstName("Новый");
            profile.setLastName("Пользователь");
            profile.setBalance(BigDecimal.ZERO);
            return repository.save(profile);
        });
    }

    public UserProfile updateProfile(Long userId, String firstName, String lastName, Integer age, String address) {
        UserProfile profile = getOrCreateProfile(userId);
        if (firstName != null) profile.setFirstName(firstName);
        if (lastName != null) profile.setLastName(lastName);
        if (age != null) profile.setAge(age);
        if (address != null) profile.setAddress(address);
        return repository.save(profile);
    }

    public UserProfile depositBalance(Long userId, BigDecimal amount) {
        UserProfile profile = getOrCreateProfile(userId);
        profile.setBalance(profile.getBalance().add(amount));
        return repository.save(profile);
    }

    public UserProfile deductBalance(Long userId, BigDecimal amount) {
        UserProfile profile = getOrCreateProfile(userId);
        if (profile.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств на балансе");
        }
        profile.setBalance(profile.getBalance().subtract(amount));
        return repository.save(profile);
    }
}