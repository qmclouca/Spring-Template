package com.qmclouca.base.services.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.repositories.ProductRepository;
import com.qmclouca.base.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Optional<List<ProductDto>> GetProductsByName(String name) {
        Optional<List<Product>> productList = productRepository.findAllByNameContainingIgnoreCase(name);

        if (productList.isEmpty()){
            return Optional.empty();
        }

        List<ProductDto> lstProductDto = productList.get().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return Optional.of(lstProductDto);
    }

    @Override
    public boolean deleteProductByName(String name) {
        Optional<List<Product>> lstProductOpt = productRepository.findAllByNameContainingIgnoreCase(name);
        if (lstProductOpt.isPresent()) {
            List<Product> lstProduct = lstProductOpt.get();
            for (Product p : lstProduct) {
                if (p.getName().equals(name)) {
                    productRepository.delete(p);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(Math.toIntExact(id));
        if (product.isPresent()){
            productRepository.delete(product.orElse(new Product()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ProductDto> GetAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> lstProductDto = new ArrayList<>();
        for(Product p: products){
            lstProductDto.add(mapToDto(p));
        }
        return lstProductDto;
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setModifiedAt(product.getModifiedAt());
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
