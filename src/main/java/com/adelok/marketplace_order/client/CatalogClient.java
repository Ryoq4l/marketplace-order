package com.adelok.marketplace_order.client;

import com.adelok.marketplace_order.dto.listing.ListingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "marketplace-catalog")
public interface CatalogClient {
    @GetMapping("/api/v1/items/{id}")
    ListingResponse getListing(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/items/{id}/sold")
    void markAsSold (@PathVariable("id") UUID id, @RequestParam("buyerId") UUID buyerId);

    @PostMapping("/api/v1/items/{id}/reserve")
    void markAsReserved(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/items/{id}/cancel")
    void cancelReservation(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/items/sell-to-system")
    void sellToSystem(
            @RequestParam("userItemId") UUID userItemId,
            @RequestParam("sellerId") UUID sellerId,
            @RequestParam("price") BigDecimal price
    );
}
