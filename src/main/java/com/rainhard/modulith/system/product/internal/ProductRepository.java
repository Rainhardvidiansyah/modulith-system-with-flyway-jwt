package com.rainhard.modulith.system.product.internal;

public interface ProductRepository {
    void saveProduct(String email, String password);
    String getProductById(String id);
    String getProductByProductName(String productName);
    void deleteProductById(String id);
    void getProductBySku(String sku);
}
