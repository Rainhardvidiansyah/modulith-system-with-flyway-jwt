package com.rainhard.modulith.system.order.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
