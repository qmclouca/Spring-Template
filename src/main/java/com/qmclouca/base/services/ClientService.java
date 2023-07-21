package com.qmclouca.base.services;

import com.qmclouca.base.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient(Client client);
    List<Client> getClientsByName(String name);
    List<Client> getAllClients();
    boolean deleteClient(String name);
    boolean deleteClient(Long id);
    Optional<Client> getClientByName(String name);
    Client saveClient(Client client);
}
