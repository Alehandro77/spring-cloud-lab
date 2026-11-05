package com.example.frontend.controller;

import com.example.frontend.client.AdminClient;
import com.example.frontend.client.ApiClient;
import com.example.frontend.client.ApiException;
import com.example.frontend.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class AdminPageController {

    private final ApiClient api;
    private final AdminClient adminClient;
    private final SessionManager session;

    public AdminPageController(ApiClient api, AdminClient adminClient, SessionManager session) {
        this.api = api;
        this.adminClient = adminClient;
        this.session = session;
    }

    @GetMapping("/admin")
    public String admin(HttpSession httpSession, Model model) {
        if (!session.isAdmin(httpSession)) return "redirect:/access-denied";
        try {
            List<Map<String, Object>> users = api.getList(
                    "/api/admin/users",
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {},
                    session.getToken(httpSession));
            List<Map<String, Object>> products = api.getList(
                    "/api/admin/products",
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {},
                    session.getToken(httpSession));
            model.addAttribute("users", users);
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "admin";
    }

    @PostMapping("/admin/products/create")
    public String createProduct(@RequestParam String name,
                                @RequestParam BigDecimal price,
                                @RequestParam(required = false) String brand,
                                @RequestParam(required = false) String color,
                                @RequestParam Long categoryId,
                                @RequestParam String gender,
                                @RequestParam Integer stockQuantity,
                                @RequestParam(required = false) String sizes,
                                HttpSession httpSession) {
        if (!session.isAdmin(httpSession)) return "redirect:/access-denied";

        List<String> sizesList = sizes == null || sizes.isEmpty() ? List.of("M") :
                Arrays.asList(sizes.split(","));

        try {
            var body = Map.of(
                    "name", name, "price", price,
                    "brand", brand == null ? "" : brand,
                    "color", color == null ? "" : color,
                    "categoryId", categoryId,
                    "gender", gender,
                    "sizes", sizesList,
                    "stockQuantity", stockQuantity,
                    "description", "", "imageUrl", ""
            );
            adminClient.createProduct(body, session.getToken(httpSession));
        } catch (ApiException ignored) {}
        return "redirect:/admin";
    }

    @PostMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession httpSession) {
        if (!session.isAdmin(httpSession)) return "redirect:/access-denied";
        try {
            adminClient.deleteProduct(id, session.getToken(httpSession));
        } catch (ApiException ignored) {}
        return "redirect:/admin";
    }
}