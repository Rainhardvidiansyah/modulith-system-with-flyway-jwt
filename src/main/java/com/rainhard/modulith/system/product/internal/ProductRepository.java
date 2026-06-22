package com.rainhard.modulith.system.product.internal;

import com.rainhard.modulith.system.product.dto.ProductRequest;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void saveProduct(ProductRequest productRequest);
    Optional<ProductsEntity> getProductById(UUID id);
    String getProductByProductName(String productName);
    void deleteProductById(UUID id);
    void getProductBySku(String sku);
}
