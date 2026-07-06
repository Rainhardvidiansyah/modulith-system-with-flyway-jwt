package com.rainhard.modulith.system.product.controller;


import com.rainhard.modulith.system.product.ProductFacade;
import com.rainhard.modulith.system.product.dto.ProductRequest;
import com.rainhard.modulith.system.product.dto.ProductResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductFacade productFacade;


    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<@NonNull ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        var facadeSaveProduct = productFacade.saveProduct(productRequest);
        LOGGER.info("this is get hit....");
        return ResponseEntity.status(HttpStatus.CREATED).body(facadeSaveProduct);
    }

    @GetMapping("/")
    public ResponseEntity<@NonNull List<ProductResponse>> getAllProduct(){

        var allProducts = productFacade.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping("/{id}")
    public ProductResponse getOneProduct(@PathVariable("id") UUID id){
        return productFacade.findProductById(id);
    }

}
