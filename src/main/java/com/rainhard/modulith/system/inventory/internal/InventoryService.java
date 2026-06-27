package com.rainhard.modulith.system.inventory.internal;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private final JpaInventoryRepository inventoryRepository;

    InventoryService(JpaInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    //Get hit when ProductCreated event triggered — init first stock
    @Transactional
    public Inventory initStock(UUID productId, String warehouseCode, int quantityAvailable) {
        LOGGER.info("Init Stock inventory get Hit");

        var inventory = Inventory.create(productId, warehouseCode, quantityAvailable);
        return inventoryRepository.save(inventory);
    }

    // Customer checkout → Lock the stock
    @Transactional
    public Inventory reserveStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));
        inventory.reserve(quantity);
        return inventoryRepository.save(inventory);
    }

    // Customer canceling the order / timeout → return the stock
    @Transactional
    public Inventory releaseStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));
        inventory.release(quantity);
        return inventoryRepository.save(inventory);
    }

    // Customer paid the bill → decrease stock permanently
    @Transactional
    public Inventory deductStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));
        inventory.deduct(quantity);
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventories(){
        return inventoryRepository.findAll();
    }
}
