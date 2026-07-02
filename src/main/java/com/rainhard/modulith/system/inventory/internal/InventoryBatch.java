package com.rainhard.modulith.system.inventory.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory_batch")
public class InventoryBatch {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "inventory_id", nullable = false, updatable = false)
    private UUID inventoryId;

    @Column(name = "batch_number", nullable = false, length = 50, updatable = false)
    private String batchNumber;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "received_date", nullable = false, updatable = false)
    private Instant receivedAt;

    @Column(name = "cost_price", nullable = false, precision = 12, scale = 2, updatable = false)
    private BigDecimal costPrice;

    protected InventoryBatch() {}

    //START OF BUSINESS METHODS
    public static InventoryBatch create(UUID inventoryId, String batchNumber,
                                        int quantity, BigDecimal costPrice) {
        var batch = new InventoryBatch();
        batch.inventoryId = inventoryId;
        batch.batchNumber = batchNumber;
        batch.quantity = quantity;
        batch.costPrice = costPrice;
        batch.receivedAt = Instant.now();
        return batch;
    }

    // business method — FIFO: reduce the quantity in this batch
    public void deduct(int amount) {
        if (amount > this.quantity) {
            throw new IllegalArgumentException("Insufficient batch quantity");
        }
        this.quantity -= amount;
    }
    //END OF BUSINESS METHODS

    // getters only
    public UUID getId() { return id; }
    public UUID getInventoryId() { return inventoryId; }
    public String getBatchNumber() { return batchNumber; }
    public int getQuantity() { return quantity; }
    public Instant getReceivedAt() { return receivedAt; }
    public BigDecimal getCostPrice() { return costPrice; }
}