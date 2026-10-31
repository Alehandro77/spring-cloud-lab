package com.example.authservice.service;

import com.example.authservice.dto.UserResponse;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.User;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(String email, String password, String firstName,
                           String lastName, String phone) {
        log.info("Registering user: email={}", email);
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль USER не найдена в базе"));

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setRole(userRole);
        user.setIsActive(true);

        userRepository.save(user);
        log.info("User registered: email={}, role={}", email, userRole.getName());
        return "Пользователь успешно зарегистрирован!";
    }

    public String login(String email, String password) {
        log.info("Login attempt: email={}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Неверный пароль");
        }
        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("Аккаунт заблокирован");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().getName());
        log.info("User logged in: email={}, role={}", email, user.getRole().getName());
        return token;
    }

    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + email));
        return toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users (ADMIN request)");
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getPhone(),
                u.getRole().getName(),
                u.getIsActive(),
                u.getCreatedAt()
        );
    }
}