package com.rainhard.modulith.system.product.internal;
import com.rainhard.modulith.system.product.ProductCreated;
import com.rainhard.modulith.system.product.dto.ProductResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final JpaProductRepository jpaProductRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    ProductService(JpaProductRepository jpaProductRepository,
                   ApplicationEventPublisher applicationEventPublisher) {
        this.jpaProductRepository = jpaProductRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Transactional
    public Product saveProduct(String name, String description, BigDecimal price){

        var product = Product.create(name, description, price);
        var savedProduct = jpaProductRepository.save(product);

        applicationEventPublisher.publishEvent(new ProductCreated(savedProduct.getId()));
        LOGGER.info("ProductCreated [after] published: {}", savedProduct.getId());

        return savedProduct;
    }

    public List<ProductResponse> findAllProducts(){
        return jpaProductRepository.findAll().stream()
                .map(product -> ProductResponse.from(product))
                .toList();
    }

    public Optional<ProductResponse> findProductById(UUID id){
        return jpaProductRepository.findById(id)
                .map(ProductResponse::from);
    }




}
