package com.example.frontend.controller;

import com.example.frontend.client.ApiException;
import com.example.frontend.client.OrderClient;
import com.example.frontend.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CartPageController {

    private final OrderClient orderClient;
    private final SessionManager session;

    public CartPageController(OrderClient orderClient, SessionManager session) {
        this.orderClient = orderClient;
        this.session = session;
    }

    @GetMapping("/cart")
    public String cart(HttpSession httpSession, Model model) {
        if (!session.isLoggedIn(httpSession)) return "redirect:/login";
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        try {
            Map<String, Object> cart = orderClient.getCart(userId, session.getToken(httpSession));
            model.addAttribute("cart", cart);
        } catch (ApiException e) {
            model.addAttribute("error", e.getUnavailable()
                    ? "Корзина временно недоступна"
                    : "Ошибка загрузки корзины");
        }
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            HttpSession httpSession) {
        if (!session.isLoggedIn(httpSession)) return "redirect:/login";
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        try {
            orderClient.addToCart(userId, productId, quantity, session.getToken(httpSession));
        } catch (ApiException ignored) {}
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{itemId}")
    public String removeFromCart(@PathVariable Long itemId, HttpSession httpSession) {
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        try {
            orderClient.removeCartItem(userId, itemId, session.getToken(httpSession));
        } catch (ApiException ignored) {}
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(HttpSession httpSession) {
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        try {
            orderClient.createOrder(userId, session.getToken(httpSession));
        } catch (ApiException ignored) {}
        return "redirect:/orders";
    }
}