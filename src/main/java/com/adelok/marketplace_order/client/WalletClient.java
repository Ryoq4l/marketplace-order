package com.adelok.marketplace_order.client;

import com.adelok.marketplace_order.dto.wallet.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "marketplace-wallet")
public interface WalletClient {
    @PostMapping("/api/v1/wallets/user/{userId}/complete")
    void complete(@RequestBody CompleteRequest request, @PathVariable("userId")UUID userId);

    @PostMapping("/api/v1/wallets/user/{userId}/deposit")
    void deposit(@RequestBody DepositRequest request, @PathVariable("userId") UUID userId);

    @PostMapping("/api/v1/wallets/user/{userId}/withdraw")
    void withdraw(@RequestBody WithdrawRequest request, @PathVariable("userId") UUID userId);

    @PostMapping("/api/v1/wallets/user/{userId}/hold")
    void hold(@RequestBody HoldRequest request, @PathVariable("userId") UUID userId);

    @PostMapping("/api/v1/wallets/user/{userId}/refund")
    void refund(@RequestBody RefundRequest request, @PathVariable("userId") UUID userId);

    @PostMapping("/api/v1/wallets/user/{userId}/release")
    void release(@RequestBody ReleaseRequest request, @PathVariable("userId") UUID userId);
}
