package com.adelok.marketplace_order.dto.listing;

import java.math.BigDecimal;
import java.util.UUID;

public record ListingResponse(
        UUID id,
        BigDecimal price,
        String status,
        SkinResponse skin,
        UUID sellerId,
        UUID ownerId
) {
}
