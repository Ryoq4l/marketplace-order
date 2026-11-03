package com.adelok.marketplace_order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateOrderRequest(
        @NotNull
        UUID listingId,
        @NotNull
        UUID buyerId
) {
}
