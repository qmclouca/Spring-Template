package com.qmclouca.base.services.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmclouca.base.Dtos.ProductDto;
import com.qmclouca.base.models.Product;
import com.qmclouca.base.repositories.ProductRepository;
import com.qmclouca.base.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Product createProduct(Product productDto) {
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

    public Optional<List<ProductDto>> getProductsByName(String name) {
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
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> lstProductDto = new ArrayList<>();
        for(Product p: products){
            lstProductDto.add(mapToDto(p));
        }
        return lstProductDto;
    }

    public ProductDto getProductById(Long id) {
        ProductDto response = new ProductDto();
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            response = mapToDto(product.orElse(new Product()));
        }
        else {
            return new ProductDto();
        }
        return response;
    }

    @Transactional
    public Optional<ProductDto> putProductById(ProductDto productUpdated) {
        Optional<Product> existingProduct = productRepository.findById(productUpdated.getId());
        if (existingProduct.isPresent()) {
            Product newProduct = existingProduct.get();
            if (!Objects.equals(newProduct.getPrice(), productUpdated.getPrice())) {
                newProduct.setPrice(productUpdated.getPrice());
            }
            if (!Objects.equals(newProduct.getName(), productUpdated.getName())) {
                newProduct.setName(productUpdated.getName());
            }
            if (!Objects.equals(newProduct.getModel(), productUpdated.getModel())) {
                newProduct.setModel(productUpdated.getModel());
            }
            if (!Objects.equals(newProduct.getCategory(), productUpdated.getCategory())) {
                newProduct.setCategory(productUpdated.getCategory());
            }
            if (!Objects.equals(newProduct.getUnity(), productUpdated.getUnity())) {
                newProduct.setUnity(productUpdated.getUnity());
            }
            if (!Objects.equals(newProduct.getMinQuantity(), productUpdated.getMinQuantity())) {
                newProduct.setMinQuantity(productUpdated.getMinQuantity());
            }
            if (!Objects.equals(newProduct.getPhysicalState(), productUpdated.getPhysicalState())) {
                newProduct.setPhysicalState(productUpdated.getPhysicalState());
            }
            newProduct.setModifiedAt(LocalDateTime.now());
            productRepository.save(newProduct);
            return Optional.of(mapToDto(newProduct));
        } else {
            return Optional.empty();
        }
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
