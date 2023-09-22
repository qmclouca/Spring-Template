package com.qmclouca.base.repositories.Implementations;

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
    public Optional<Product> findByNameContainingIgnoreCase(String name) {
        String query = "SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)";
        List<Product> result = entityManager.createQuery(query, Product.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getResultList();
        if (result != null){
           return Optional.of(result.get(0));
        } else {
           return Optional.empty();
        }
    }
}
