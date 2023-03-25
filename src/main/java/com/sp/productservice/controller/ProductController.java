package com.sp.productservice.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.productservice.dto.ProductRequest;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    public void createProduct(@RequestBody ProductRequest productRequest) {
        
    }
}
