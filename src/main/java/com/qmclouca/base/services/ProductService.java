package com.qmclouca.base.services;

import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    Product createProduct(Product productDto);
    Optional<List<ProductDto>> getProductsByName(String name);
    boolean deleteProductByName(String name);
    boolean deleteProductById(Long id);
    Optional<ProductDto> putProductById(ProductDto productUpdated);
}
