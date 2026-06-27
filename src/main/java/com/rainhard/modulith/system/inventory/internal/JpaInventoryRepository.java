package com.rainhard.modulith.system.inventory.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaInventoryRepository extends JpaRepository<Inventory, UUID> {
}
