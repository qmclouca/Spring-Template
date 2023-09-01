package com.qmclouca.base.services;

import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> GetAllProducts();
    Product CreateProduct(Product productDto);
    Optional<ProductDto> getProductsByName(String name);
}
