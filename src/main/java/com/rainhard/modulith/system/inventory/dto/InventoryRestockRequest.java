package com.rainhard.modulith.system.inventory.dto;

import java.math.BigDecimal;

public class InventoryRestockRequest {

    private int quantity;
    private BigDecimal costPrice;

    public InventoryRestockRequest(int quantity, BigDecimal costPrice) {
        this.quantity = quantity;
        this.costPrice = costPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
}
