package com.qmclouca.base.services;

import com.qmclouca.base.models.Client;

import java.util.Collection;

public interface ClientService {
    public abstract Client createClient(Client client);
    public abstract void deleteClient(Integer id);
    public abstract void update(Integer id, Client client);
    public abstract Collection<Client> getClients();
    public abstract Client getCLientByName();
}
