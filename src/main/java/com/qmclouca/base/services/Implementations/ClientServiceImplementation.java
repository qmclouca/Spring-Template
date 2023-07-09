package com.qmclouca.base.services.Implementations;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.repositories.ClientRepository;
import com.qmclouca.base.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Service
public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImplementation(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Integer id) {

    }

    @Override
    public void update(Integer id, Client client) {

    }

    @Override
    public Collection<Client> getClients() {
        return null;
    }

    @Override
    public Client getCLientByName() {
        return null;
    }
}
