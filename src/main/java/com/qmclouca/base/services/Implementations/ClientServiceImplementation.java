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

    public ClientServiceImplementation(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

}
