package com.rainhard.modulith.system.inventory.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaInventoryBatchRepository extends JpaRepository<InventoryBatch, UUID> {

    List<InventoryBatch> findByInventoryIdAndQuantityGreaterThanOrderByReceivedAtAsc(UUID id, int quantity);
}
