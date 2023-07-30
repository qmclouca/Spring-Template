package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> getClientsByName(String name);

    Optional<Client> getClientByName(String name);

    Optional<Client> getClientById(Long id);

    Optional<Client> findByClientNameAndPassword(String userName, String password);
}
