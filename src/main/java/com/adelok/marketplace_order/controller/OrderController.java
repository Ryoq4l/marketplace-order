package com.adelok.marketplace_order.controller;

import com.adelok.marketplace_order.dto.CreateOrderRequest;
import com.adelok.marketplace_order.dto.SellOrderRequest;
import com.adelok.marketplace_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void buy(@RequestBody CreateOrderRequest request) {
        System.out.println("DEBUG Request: " + request); // Проверим, что пришло
        orderService.buy(request);
    }
    @PostMapping("/sell")
    public void sell(@RequestBody SellOrderRequest request){
        orderService.sell(request);
    }
}
