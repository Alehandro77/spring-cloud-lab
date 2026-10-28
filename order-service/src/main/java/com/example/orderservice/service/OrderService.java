package com.example.orderservice.service;

import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.OrderStatus;
import com.example.orderservice.repository.CartRepository;
import com.example.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;

    public OrderService(OrderRepository orderRepo, CartRepository cartRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    @Transactional
    public OrderResponse createFromCart(Long buyerId) {
        log.info("Creating order for buyer {}", buyerId);

        Cart cart = cartRepo.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("Корзина пуста"));
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Корзина пуста");
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setStatus(OrderStatus.CREATED);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProductId(ci.getProductId());
            oi.setProductName(ci.getTitle());
            oi.setPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            order.getItems().add(oi);
            total = total.add(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        order.setTotalPrice(total);

        Order saved = orderRepo.save(order);
        cartRepo.delete(cart);    // корзина очищается после заказа
        log.info("Order {} created for buyer {}, total = {}", saved.getId(), buyerId, total);
        return toResponse(saved);
    }

    public OrderResponse getById(Long id) {
        return toResponse(orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: " + id)));
    }

    public List<OrderResponse> getByBuyer(Long buyerId) {
        return orderRepo.findByBuyerId(buyerId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order o = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: " + id));
        o.setStatus(status);
        return toResponse(orderRepo.save(o));
    }

    private OrderResponse toResponse(Order o) {
        List<OrderItemResponse> items = o.getItems().stream()
                .map(i -> new OrderItemResponse(i.getProductId(), i.getProductName(), i.getPrice(), i.getSize(), i.getQuantity()))
                .collect(Collectors.toList());
        return new OrderResponse(o.getId(), o.getBuyerId(), o.getStatus().name(), o.getTotalPrice(), items, o.getCreatedAt());
    }
}