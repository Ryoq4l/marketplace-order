package com.adelok.marketplace_order.service;

import com.adelok.marketplace_order.client.CatalogClient;
import com.adelok.marketplace_order.client.WalletClient;
import com.adelok.marketplace_order.dto.CreateOrderRequest;
import com.adelok.marketplace_order.dto.SellOrderRequest;
import com.adelok.marketplace_order.dto.listing.ListingResponse;
import com.adelok.marketplace_order.dto.wallet.*;
import com.adelok.marketplace_order.entity.Order;
import com.adelok.marketplace_order.entity.enumerated.OrderStatus;
import com.adelok.marketplace_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${app.trade-protection-minutes}")
    long tradeProtectionMinutes;
    @Value("${app.mock-seller-id}")
    UUID sellerId;
    private final OrderRepository orderRepository;
    private final WalletClient walletClient;
    private final CatalogClient catalogClient;

    //    public void buy(CreateOrderRequest request) {
//        ListingResponse listing = catalogClient.getListing(request.listingId());
//        if (!listing.status().equals("AVAILABLE")) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Listing is not available");
//        }
//        Order order = Order.builder()
//                .sellerId(sellerId)
//                .buyerId(request.buyerId())
//                .listingId(request.listingId())
//                .price(listing.price())
//                .skinId(listing.skin().id())
//                .status(OrderStatus.CREATED)
//                .build();
//        orderRepository.save(order);
//        catalogClient.markAsReserved(listing.id());
//        try {
//            HoldRequest holdRequest = new HoldRequest(listing.price(), order.getId(), "Bought");
//            walletClient.hold(holdRequest, request.buyerId());
//            catalogClient.markAsSold(request.listingId(), request.buyerId());
//            CompleteRequest completeRequest = new CompleteRequest(listing.price(), order.getId(), "Deal complete");
//            walletClient.withdraw(completeRequest, sellerId);
//            order.setStatus(OrderStatus.COMPLETED);
//            orderRepository.save(order);
//        } catch (Exception e1) {
//            try {
//                catalogClient.cancelReservation(listing.id());
//                RefundRequest refundRequest = new RefundRequest(listing.price(), order.getId(), "Refund");
//                walletClient.refund(refundRequest, request.buyerId());
//            } catch (Exception e2) {
//            }
//            order.setStatus(OrderStatus.CANCELLED);
//            orderRepository.save(order);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed");
//        }
//    }
    public void buy(CreateOrderRequest request) {
        ListingResponse listing = catalogClient.getListing(request.listingId());
        if (!listing.status().equals("AVAILABLE")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Listing is not available");
        }
        Order order = Order.builder()
                .sellerId(sellerId)
                .buyerId(request.buyerId())
                .listingId(request.listingId())
                .price(listing.price())
                .skinId(listing.skin().id())
                .status(OrderStatus.CREATED)
                .build();
        orderRepository.save(order);
        catalogClient.markAsReserved(listing.id());

        try {
            WithdrawRequest withdrawRequest = new WithdrawRequest(listing.price(), "Bought");
            walletClient.withdraw(withdrawRequest, request.buyerId());

            catalogClient.markAsSold(request.listingId(), request.buyerId());

            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                catalogClient.cancelReservation(listing.id());
                RefundRequest refundRequest = new RefundRequest(listing.price(), order.getId(), "Refund");
                walletClient.refund(refundRequest, request.buyerId());
            } catch (Exception e2) {
                System.err.println("Compensation failed: " + e2.getMessage());
            }
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed");
        }
    }

    public void sell(SellOrderRequest request) {
        HoldRequest holdRequest = new HoldRequest(request.price(), UUID.randomUUID(), "Sell skin");
        Order order = Order.builder()
                .sellerId(request.sellerId())
                .price(request.price())
                .status(OrderStatus.PENDING_SELL)
                .holdExpiresAt(LocalDateTime.now().plusMinutes(tradeProtectionMinutes))
                .build();
        orderRepository.save(order);
        HoldRequest hold = new HoldRequest(request.price(), order.getId(), "Sell skin");
        walletClient.hold(hold, request.sellerId());
        catalogClient.sellToSystem(request.userItemId(), request.sellerId(), request.price());
    }
}
