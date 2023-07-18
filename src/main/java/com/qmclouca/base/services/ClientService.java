package com.qmclouca.base.services;

import com.qmclouca.base.models.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    List<Client> getClientsByName(String name);
    List<Client> getAllClients();
    boolean deleteClient(String name);
    //boolean deleteClient(Long clientId);
}
