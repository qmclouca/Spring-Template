package com.qmclouca.base.controllers;
import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper){
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto postProductDto) {
        Product productRequest = modelMapper.map(postProductDto, Product.class);

        if (productService.getProductsByName(postProductDto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Product product = productService.createProduct(productRequest);

        ProductDto postProductResponse = modelMapper.map(product, ProductDto.class);

        if (postProductResponse != null){
            return ResponseEntity.ok(postProductResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        Optional<List<ProductDto>> productsOpt = productService.getProductsByName(name);

        if (!productsOpt.isPresent() || productsOpt.get().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productsOpt.get());
    }

    @DeleteMapping("deleteByName/{name}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable String name){
        boolean isDeleted =  productService.deleteProductByName(name);
        return ResponseEntity.ok(isDeleted);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Long id){
        boolean isDeleted = productService.deleteProductById(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("putById/{id}")
    public ResponseEntity<?> putProductById(@PathVariable Long id, @RequestBody ProductDto productToChange){
        ProductDto actualProduct = productService.getProductById(id);
        if (actualProduct == null){
            return ResponseEntity.notFound().build();
        }
        if (actualProduct.equals(productToChange)){
            return ResponseEntity.badRequest().body("O produto fornecido já existe no banco de dados.");
        }
        Optional<ProductDto> response = productService.putProductById(productToChange);
        return ResponseEntity.ok(response);
    }
}
