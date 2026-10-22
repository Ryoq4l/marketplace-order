package com.adelok.marketplace_order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SellOrderRequest(
        UUID userItemId,
        UUID sellerId,
        BigDecimal price
) {}
