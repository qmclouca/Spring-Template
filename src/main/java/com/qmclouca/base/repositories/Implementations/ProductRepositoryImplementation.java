package com.qmclouca.base.repositories.Implementations;

import com.qmclouca.base.controllers.ProductController;
import com.qmclouca.base.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class ProductRepositoryImplementation {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<List<Product>> findAllByNameContainingIgnoreCase(String name){
        String query = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')";
        List<Product> result = entityManager.createQuery(query, Product.class)
                    .setParameter("name", name)
                    .getResultList();
        if (result != null){
           return Optional.of(result);
        } else {
           return Optional.empty();
        }
    }

    public Optional<Product> findById (Long id){
        String query = "SELECT p FROM Product p WHERE p.id = id";
        Product result = entityManager.createQuery(query, Product.class)
                .setParameter("id", id)
                .getSingleResult();
        if (result != null){
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }
}
