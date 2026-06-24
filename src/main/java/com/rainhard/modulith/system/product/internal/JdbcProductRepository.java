package com.rainhard.modulith.system.product.internal;

import com.rainhard.modulith.system.product.dto.ProductRequest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class JdbcProductRepository implements ProductRepository{


    private final JdbcClient jdbcClient;

    JdbcProductRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }


    @Override
    public void saveProduct(ProductRequest productRequest) {
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return jdbcClient.sql("SELECT * FROM products WHERE id= :id")
                .param("id", id)
                .query(Product.class)
                .optional();
    }


    @Override
    public String getProductByProductName(String productName) {
        return "";
    }


    @Override
    public void deleteProductById(UUID id) {

    }

    @Override
    public void getProductBySku(String sku) {

    }
}
