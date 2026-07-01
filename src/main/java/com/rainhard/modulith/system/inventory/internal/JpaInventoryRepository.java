package com.rainhard.modulith.system.inventory.internal;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaInventoryRepository extends JpaRepository<Inventory, UUID> {

    //Use class name: Inventory
    //Use property name: productId
    //Don't use table name: inventory and column name: product_id
    //it would be a different story if nativeQuery being used
    @Query("SELECT i from Inventory i WHERE i.productId = :productId")
    Optional<Inventory> findInventoryByProductId(@Param("productId") UUID productId);

}
