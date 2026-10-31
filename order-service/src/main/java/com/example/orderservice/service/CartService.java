package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.dto.ProductDto;
import com.example.orderservice.dto.AddToCartRequest;
import com.example.orderservice.dto.CartResponse;
import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService {

    private final CartRepository cartRepo;
    private final ProductClient productClient;

    public CartService(CartRepository cartRepo, ProductClient productClient) {
        this.cartRepo = cartRepo;
        this.productClient = productClient;
    }

    public CartResponse getCart(Long buyerId) {
        Cart cart = cartRepo.findByBuyerId(buyerId).orElseGet(() -> createEmpty(buyerId));
        return toResponse(cart);
    }

    public CartResponse addItem(Long buyerId, AddToCartRequest req) {
        log.info("Adding product {} x{} to cart of buyer {}", req.productId(), req.quantity(), buyerId);

        ProductDto product = productClient.getProduct(req.productId());
        if (product == null || !Boolean.TRUE.equals(product.isActive())) {
            throw new RuntimeException("Товар недоступен: " + req.productId());
        }
        if (product.stockQuantity() < req.quantity()) {
            throw new RuntimeException("Недостаточно товара на складе: " + product.name());
        }

        Cart cart = cartRepo.findByBuyerId(buyerId).orElseGet(() -> createEmpty(buyerId));
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(req.productId()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + req.quantity());
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductId(product.id());
            item.setTitle(product.name());
            item.setPrice(product.price());
            item.setQuantity(req.quantity());
            cart.getItems().add(item);
        }

        return toResponse(cartRepo.save(cart));
    }

    public CartResponse updateItem(Long buyerId, Long itemId, Integer quantity) {
        Cart cart = cartRepo.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("Корзина не найдена"));
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Товар в корзине не найден: " + itemId));
        item.setQuantity(quantity);
        return toResponse(cartRepo.save(cart));
    }

    public CartResponse removeItem(Long buyerId, Long itemId) {
        Cart cart = cartRepo.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("Корзина не найдена"));
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return toResponse(cartRepo.save(cart));
    }

    public void clear(Long buyerId) {
        cartRepo.findByBuyerId(buyerId).ifPresent(cartRepo::delete);
    }

    private Cart createEmpty(Long buyerId) {
        Cart cart = new Cart();
        cart.setBuyerId(buyerId);
        return cartRepo.save(cart);
    }

    private CartResponse toResponse(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartResponse.CartItemResponse> items = cart.getItems().stream()
                .map(i -> new CartResponse.CartItemResponse(
                        i.getId(), i.getProductId(), i.getTitle(), i.getPrice(), i.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getId(), cart.getBuyerId(), items, total);
    }
}