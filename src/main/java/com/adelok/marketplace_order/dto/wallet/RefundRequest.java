package com.adelok.marketplace_order.dto.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record RefundRequest(
        BigDecimal amount,
        UUID dealId,
        String description
) {
}
