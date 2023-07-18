package com.qmclouca.base.services.Implementations;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.repositories.ClientRepository;
import com.qmclouca.base.services.ClientService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public List<Client> getClientsByName(String name) {
        return clientRepository.getClientsByName(name);
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public boolean deleteClient(String name){
        Optional<Client> client = clientRepository.getClientByName(name);
        if (client.isPresent()){
            clientRepository.delete(client.orElse(new Client()));
            return true;
        } else {
            return false;
        }
    }
}
