package com.adelok.marketplace_order.dto;

import java.util.UUID;

public record SellOrderRequest(
        UUID userItemId,
        UUID sellerId
) {}
