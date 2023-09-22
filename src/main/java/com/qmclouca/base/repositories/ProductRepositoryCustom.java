package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Product;
import java.util.Optional;

public interface ProductRepositoryCustom {
    Optional<Product> findByNameContainingIgnoreCase(String name);
}
