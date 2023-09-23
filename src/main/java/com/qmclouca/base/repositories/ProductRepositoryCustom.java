package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
    Optional<List<Product>> findAllByNameContainingIgnoreCase(String name);
}
