package com.adelok.marketplace_order.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record CreateOrderRequest(
UUID listingId,
UUID buyerId
) {
}
