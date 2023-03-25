package com.sp.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sp.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
}
