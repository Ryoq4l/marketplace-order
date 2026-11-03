package com.adelok.marketplace_order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SellOrderRequest(
        @NotNull
        UUID userItemId,
        @NotNull
        UUID sellerId
) {}
