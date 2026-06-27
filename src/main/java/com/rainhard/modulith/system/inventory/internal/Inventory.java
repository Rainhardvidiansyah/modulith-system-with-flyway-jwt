package com.rainhard.modulith.system.inventory.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "warehouse_code", nullable = false, length = 50)
    private String warehouseCode;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable = 0;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity = 0;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    protected Inventory(){}

    public static Inventory create(UUID productId, String warehouseCode, int quantityAvailable){
        var inventory = new Inventory();
        inventory.productId = productId;
        inventory.warehouseCode = warehouseCode;
        inventory.reservedQuantity = 0;
        inventory.quantityAvailable = quantityAvailable;
        inventory.lastUpdated = Instant.now();
        return inventory;
    }

    //Book the product
    public void reserve(int quantity) {
        if (quantity > quantityAvailable) {
            throw new IllegalArgumentException("Insufficient stock to reserve");
        }
        this.quantityAvailable -= quantity;
        this.reservedQuantity += quantity;
        this.lastUpdated = Instant.now();
    }

    //Canceled order
    public void release(int quantity) {
        if (quantity > reservedQuantity) {
            throw new IllegalArgumentException("Cannot release more than reserved");
        }
        this.reservedQuantity -= quantity;
        this.quantityAvailable += quantity;
        this.lastUpdated = Instant.now();
    }

    //Paid order
    public void deduct(int quantity) {
        if (quantity > reservedQuantity) {
            throw new IllegalArgumentException("Cannot deduct more than reserved");
        }
        this.reservedQuantity -= quantity;
        this.lastUpdated = Instant.now();
    }



    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }
}
