package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryCustom {
    List<Client> getClientsByName(String name);

    Optional<Client> getClientByName(String name);

    Optional<Client> getClientById(Long id);

    Optional<Client> findByClientNameAndPassword(String userName, String password);
}
