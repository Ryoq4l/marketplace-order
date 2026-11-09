package com.adelok.marketplace_order.controller;

import com.adelok.marketplace_order.dto.CreateOrderRequest;
import com.adelok.marketplace_order.dto.SellOrderRequest;
import com.adelok.marketplace_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/buy")
    public ResponseEntity<Void> buy(
            @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal String tokenUserId) {
        if (!request.buyerId().toString().equals(tokenUserId)) throw new RuntimeException("Access Denied");
        System.out.println("DEBUG Request: " + request);
        orderService.buy(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/sell")
    public void sell(@RequestBody SellOrderRequest request){
        orderService.sell(request);
    }
}
