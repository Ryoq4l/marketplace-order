package com.adelok.marketplace_order.repository;

import com.adelok.marketplace_order.entity.Order;
import com.adelok.marketplace_order.entity.enumerated.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository <Order, UUID>{
    Optional<Order> findByListingId(UUID id);
    List<Order> findAllByStatusAndHoldExpiresAtBefore(OrderStatus status, LocalDateTime holdExpiresAt);
}
