package com.rainhard.modulith.system.inventory.internal;


import com.rainhard.modulith.system.product.ProductCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryListener.class);
    private final InventoryService inventoryService;

    InventoryListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @ApplicationModuleListener
    public void saveCreatedProduct(ProductCreated created){
        LOGGER.info("ProductCreated received: {}", created.productId());
        inventoryService.initStock(created.productId(), "wh-bandung", 100);
    }
}
