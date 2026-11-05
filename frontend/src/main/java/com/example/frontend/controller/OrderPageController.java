package com.example.frontend.controller;

import com.example.frontend.client.ApiException;
import com.example.frontend.client.OrderClient;
import com.example.frontend.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class OrderPageController {

    private final OrderClient orderClient;
    private final SessionManager session;

    public OrderPageController(OrderClient orderClient, SessionManager session) {
        this.orderClient = orderClient;
        this.session = session;
    }

    @GetMapping("/orders")
    public String orders(HttpSession httpSession, Model model) {
        if (!session.isLoggedIn(httpSession)) return "redirect:/login";
        Long userId = session.getUserId(httpSession);
        if (userId == null) return "redirect:/login";
        try {
            List<Map<String, Object>> orders = orderClient.getMyOrders(userId, session.getToken(httpSession));
            model.addAttribute("orders", orders);
        } catch (ApiException e) {
            model.addAttribute("error", e.getUnavailable()
                    ? "Заказы временно недоступны"
                    : "Ошибка загрузки заказов");
        }
        return "orders";
    }
}