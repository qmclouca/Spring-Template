package com.qmclouca.base.controllers;

import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        super();
        this.productService = productService;
    }

    @PostMapping
    public void createProduct(ProductDto productDto){
        productService.CreateProduct(productDto);
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.GetAllProducts();
    }
}
