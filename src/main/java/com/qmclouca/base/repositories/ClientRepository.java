package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientsByLastName(String name);
}
