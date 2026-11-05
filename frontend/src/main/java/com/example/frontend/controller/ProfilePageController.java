package com.example.frontend.controller;

import com.example.frontend.client.ApiClient;
import com.example.frontend.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ProfilePageController {

    private final ApiClient api;
    private final SessionManager session;

    public ProfilePageController(ApiClient api, SessionManager session) {
        this.api = api;
        this.session = session;
    }

    @GetMapping("/profile")
    public String profile(HttpSession httpSession, Model model) {
        if (!session.isLoggedIn(httpSession)) return "redirect:/login";
        try {
            Long userId = session.getUserId(httpSession);
            if (userId == null) return "redirect:/login";
            Map<String, Object> profile = api.get(
                    "/api/profile/" + userId, Map.class, session.getToken(httpSession));
            Map<String, Object> stats = api.get(
                    "/api/profile/stats/" + userId, Map.class, session.getToken(httpSession));
            model.addAttribute("profile", profile);
            model.addAttribute("stats", stats);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) Integer age,
                                @RequestParam(required = false) String address,
                                HttpSession httpSession) {
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        var body = Map.of(
                "firstName", firstName == null ? "" : firstName,
                "lastName", lastName == null ? "" : lastName,
                "age", age,
                "address", address == null ? "" : address);
        api.put("/api/profile/" + userId, body, Map.class, session.getToken(httpSession));
        return "redirect:/profile";
    }
}