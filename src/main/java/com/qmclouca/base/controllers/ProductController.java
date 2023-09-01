package com.qmclouca.base.controllers;

import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService){
        super();
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto postProductDto) {
        Product productRequest = modelMapper.map(postProductDto, Product.class);

        if (productService.getProductsByName(postProductDto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Product product = productService.CreateProduct(productRequest);

        ProductDto postProductResponse = modelMapper.map(product, ProductDto.class);

        if (postProductResponse != null){
            return ResponseEntity.ok(postProductResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.GetAllProducts();
    }
}
