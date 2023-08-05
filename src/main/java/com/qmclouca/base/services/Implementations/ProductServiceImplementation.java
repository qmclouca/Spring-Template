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

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;
    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> GetAllProducts() {
        List<Product> lstProdutos = new ArrayList<>();
        lstProdutos = productRepository.findAll();


        List<ProductDto> lstProdutosDto = new ArrayList<>();

        return lstProdutosDto;
    }
}
