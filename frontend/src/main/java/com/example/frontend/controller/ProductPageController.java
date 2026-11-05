package com.example.frontend.controller;

import com.example.frontend.client.ApiException;
import com.example.frontend.client.ProductClient;
import com.example.frontend.client.ReviewClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import com.example.frontend.session.SessionManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class ProductPageController {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;
    private final SessionManager session;

    public ProductPageController(ProductClient productClient, ReviewClient reviewClient, SessionManager session) {
        this.productClient = productClient;
        this.reviewClient = reviewClient;
        this.session = session;
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        try {
            List<Map<String, Object>> products = productClient.getAll();
            model.addAttribute("products", products);
        } catch (ApiException e) {
            model.addAttribute("error", e.getUnavailable()
                    ? "Каталог временно недоступен. Попробуйте позже."
                    : "Ошибка загрузки товаров: " + e.getMessage());
        }
        return "catalog";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        try {
            Map<String, Object> product = productClient.getById(id, null);
            model.addAttribute("product", product);
        } catch (ApiException e) {
            model.addAttribute("error", "Товар не найден или недоступен");
            return "product";
        }
        try {
            List<Map<String, Object>> reviews = reviewClient.getByProduct(id);
            model.addAttribute("reviews", reviews);
        } catch (ApiException e) {
            model.addAttribute("reviewsError", "Отзывы временно недоступны");
        }
        return "product";
    }

    @PostMapping("/reviews/create")
    public String createReview(@RequestParam Long productId,
                               @RequestParam Integer rating,
                               @RequestParam(required = false) String comment,
                               HttpSession httpSession,
                               RedirectAttributes redirectAttributes) {
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";

        try {
            Map<String, Object> body = Map.of(
                    "productId", productId,
                    "userId", userId,
                    "rating", rating,
                    "comment", comment == null ? "" : comment);
            reviewClient.create(body, session.getToken(httpSession));
            redirectAttributes.addFlashAttribute("success", "Отзыв успешно добавлен!");   // ← УСПЕХ
        } catch (ApiException e) {
            redirectAttributes.addFlashAttribute("reviewError", extractErrorMessage(e));   // ← ОШИБКА
        }
        return "redirect:/product/" + productId;
    }

    private String extractErrorMessage(ApiException e) {
        String msg = e.getMessage();
        if (msg == null) return "Ошибка при создании отзыва";
        int start = msg.indexOf("\"error\":\"");
        if (start >= 0) {
            start += 9;
            int end = msg.indexOf("\"", start);
            if (end > start) return msg.substring(start, end);
        }
        if (e.getUnavailable()) return "Сервис отзывов временно недоступен";
        return "Не удалось добавить отзыв";
    }
}