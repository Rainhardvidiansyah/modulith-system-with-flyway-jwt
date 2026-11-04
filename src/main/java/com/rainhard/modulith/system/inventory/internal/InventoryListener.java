package com.rainhard.modulith.system.inventory.internal;


import com.rainhard.modulith.system.ResourceNotFoundException;
import com.rainhard.modulith.system.order.OrderCanceled;
import com.rainhard.modulith.system.order.OrderPlaced;
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
        inventoryService.initStock(created.productId(), "wh-bandung", created.quantityAvailable());
    }

    public void saveBatch(){}


    @ApplicationModuleListener
    public void orderCreated(OrderPlaced event){
        LOGGER.info("Order created received: {}", event);

        event.items().forEach(
                item -> {
                    var inventory = inventoryService.getInventoryByProductId(item.productId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
                inventoryService.reserveStock(inventory.getId(), item.quantity());
                });
    }

    @ApplicationModuleListener
    public void onExpiredOrder(OrderCanceled event){
        LOGGER.info("Expired order received: {}", event.orderId());
        event.canceledItem().forEach(
                item -> {
                    var inventory = inventoryService.getInventoryByProductId(item.productId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    inventoryService.releaseStock(inventory.getId(), item.quantity());
                }
        );
    }


    @ApplicationModuleListener
    public void onCancelingOrderByCustomer(OrderCanceled event){

        LOGGER.info("onCancelingOrderByCustomer: {}", event.orderId());

        event.canceledItem().forEach(
                item -> {
                    var inventory = inventoryService.getInventoryByProductId(item.productId())
                                    .orElseThrow();

                    inventoryService.releaseStock(inventory.getId(), item.quantity());

                }
        );
    }




}
