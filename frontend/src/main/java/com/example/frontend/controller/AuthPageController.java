package com.example.frontend.controller;

import com.example.frontend.client.ApiClient;
import com.example.frontend.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AuthPageController {

    private final ApiClient api;
    private final SessionManager session;

    public AuthPageController(ApiClient api, SessionManager session) {
        this.api = api;
        this.session = session;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession httpSession,
                        Model model) {
        try {
            var body = Map.of("email", email, "password", password);
            var resp = api.post("/api/auth/login", body, Map.class, null);
            String token = (String) resp.get("accessToken");
            String role = extractClaimFromJwt(token, "role");
            Long userId = extractLongClaimFromJwt(token, "userId");
            session.saveToken(httpSession, token, email, role, userId);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка входа: " + e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           Model model) {
        try {
            var body = Map.of(
                    "email", email, "password", password,
                    "firstName", firstName, "lastName", lastName);
            api.post("/api/auth/register", body, Map.class, null);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка регистрации: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        this.session.logout(session);
        return "redirect:/";
    }

    private String extractClaimFromJwt(String token, String claimName) {
        try {
            String payload = new String(java.util.Base64.getDecoder()
                    .decode(token.split("\\.")[1]));
            String key = "\"" + claimName + "\":\"";
            int start = payload.indexOf(key);
            if (start < 0) return null;
            start += key.length();
            int end = payload.indexOf("\"", start);
            return payload.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    private Long extractLongClaimFromJwt(String token, String claimName) {
        try {
            String payload = new String(java.util.Base64.getDecoder()
                    .decode(token.split("\\.")[1]));
            String key = "\"" + claimName + "\":";
            int start = payload.indexOf(key);
            if (start < 0) return null;
            start += key.length();
            int end = start;
            while (end < payload.length() && Character.isDigit(payload.charAt(end))) end++;
            return Long.parseLong(payload.substring(start, end));
        } catch (Exception e) {
            return null;
        }
    }
}