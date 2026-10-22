package com.adelok.marketplace_order.entity;

import com.adelok.marketplace_order.entity.enumerated.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "seller_id",  nullable=false)
    private UUID sellerId;

    @Column(name = "buyer_id")
    private UUID buyerId;

    @Column(name = "listing_id")
    private UUID listingId;

    @Column(name= "price", nullable= false)
    private BigDecimal price;

    @Column(name = "skin_id")
    private UUID skinId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "hold_expires_at")
    private LocalDateTime holdExpiresAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
