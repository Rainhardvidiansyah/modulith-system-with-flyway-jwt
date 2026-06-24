package com.rainhard.modulith.system.product;
import com.rainhard.modulith.system.product.dto.ProductRequest;
import com.rainhard.modulith.system.product.dto.ProductResponse;
import com.rainhard.modulith.system.product.internal.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;


    public ProductResponse saveProduct(ProductRequest request){
        var savedProduct = productService.saveProduct(request.name(), request.description(), request.price());
        return ProductResponse.from(savedProduct);
    }

    public List<ProductResponse> findAllProducts(){
        return productService.findAllProducts();
    }

    public Optional<ProductResponse> findProductById(UUID id){
        return productService.findProductById(id);
    }



}
