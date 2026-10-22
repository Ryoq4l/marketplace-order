package com.adelok.marketplace_order.dto.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawRequest(
        @Positive
        @NotNull
        BigDecimal amount,

        String description
) {
}
