package com.example.orderservice.controller;

import com.example.orderservice.dto.AddToCartRequest;
import com.example.orderservice.dto.CartResponse;
import com.example.orderservice.dto.UpdateCartItemRequest;
import com.example.orderservice.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{buyerId}")
    public CartResponse getCart(@PathVariable Long buyerId) {
        return cartService.getCart(buyerId);
    }

    @PostMapping("/{buyerId}/items")
    public CartResponse addItem(@PathVariable Long buyerId, @Valid @RequestBody AddToCartRequest req) {
        return cartService.addItem(buyerId, req);
    }

    @PutMapping("/{buyerId}/items/{itemId}")
    public CartResponse updateItem(@PathVariable Long buyerId,
                                   @PathVariable Long itemId,
                                   @Valid @RequestBody UpdateCartItemRequest req) {
        return cartService.updateItem(buyerId, itemId, req.quantity());
    }

    @DeleteMapping("/{buyerId}/items/{itemId}")
    public CartResponse removeItem(@PathVariable Long buyerId, @PathVariable Long itemId) {
        return cartService.removeItem(buyerId, itemId);
    }

    @DeleteMapping("/{buyerId}")
    public void clear(@PathVariable Long buyerId) {
        cartService.clear(buyerId);
    }
}