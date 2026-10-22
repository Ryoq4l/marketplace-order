package com.adelok.marketplace_order.scheduler;

import com.adelok.marketplace_order.client.WalletClient;
import com.adelok.marketplace_order.dto.wallet.DepositRequest;
import com.adelok.marketplace_order.entity.Order;
import com.adelok.marketplace_order.entity.enumerated.OrderStatus;
import com.adelok.marketplace_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;
    private final WalletClient walletClient;

    @Scheduled(fixedRate = 60000)
    public void processExpiredSells() {
        List<Order> expiredOrders = orderRepository.findAllByStatusAndHoldExpiresAtBefore(
                OrderStatus.PENDING_SELL,
                LocalDateTime.now()
        );

        for (Order order : expiredOrders) {
            try {
                DepositRequest request = new DepositRequest(order.getPrice(), "Trade protection expired");
                walletClient.deposit(request, order.getSellerId());

                order.setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);

                log.info("Successfully completed sell order {}", order.getId());
            } catch (Exception e) {
                log.error("Failed to complete sell order {}", order.getId(), e);
            }
        }
    }
}