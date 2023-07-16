package com.qmclouca.base.services;

import com.qmclouca.base.models.Client;

import java.util.List;

public interface ClientService {
    public abstract Client createClient(Client client);
    public abstract List<Client> getClientsByName(String name);
    public abstract List<Client> getAllClients();
}
