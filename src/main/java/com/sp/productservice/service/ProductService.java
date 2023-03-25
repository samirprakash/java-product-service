package com.sp.productservice.service;

import org.springframework.stereotype.Service;

import com.sp.productservice.dto.ProductRequest;
import com.sp.productservice.model.Product;
import com.sp.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    
    public void createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} created successfully", product.getId());
    }
}
