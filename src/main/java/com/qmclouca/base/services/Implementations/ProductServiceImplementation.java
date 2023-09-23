package com.qmclouca.base.services.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.repositories.ProductRepository;
import com.qmclouca.base.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Product> products = productRepository.findAll();
        List<ProductDto> lstProductDto = new ArrayList<>();
        for(Product p: products){
            ProductDto productDto = new ProductDto();
            productDto.setId(p.getId());
            productDto.setCreatedAt(p.getCreatedAt());
            productDto.setModifiedAt(p.getModifiedAt());
            productDto.setName(p.getName());
            productDto.setCategory(p.getCategory());
            productDto.setPrice(p.getPrice());
            productDto.setModel(p.getModel());
            productDto.setUnity(p.getUnity());
            productDto.setMinQuantity(p.getMinQuantity());
            productDto.setPhysicalState(p.getPhysicalState());
            lstProductDto.add(productDto);
        }
        return lstProductDto;
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
