package com.qmclouca.base.services.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.repositories.ProductRepository;
import com.qmclouca.base.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;
    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product CreateProduct(Product productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setUnity(productDto.getUnity());
        product.setModel(productDto.getModel());
        product.setMinQuantity(productDto.getMinQuantity());
        product.setPhysicalState(productDto.getPhysicalState());
        productRepository.save(product);
        return product;
    }

    public Optional<ProductDto> getProductsByName(String name) {
        Optional<Product> product = productRepository.findByNameContainingIgnoreCase(name);

        if (product.isEmpty()){
            return Optional.empty();
        }
        ProductDto productDto = mapToDto(product.get());
        return Optional.of(productDto);
    }

    @Override
    public List<ProductDto> GetAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setUnity(product.getUnity());
        productDto.setModel(product.getModel());
        productDto.setMinQuantity(product.getMinQuantity());
        productDto.setPhysicalState(product.getPhysicalState());
        return productDto;
    }

}
