package com.qmclouca.base.services.Implementations;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.repositories.ClientRepository;
import com.qmclouca.base.services.ClientService;
import jakarta.persistence.NoResultException;
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

    public Optional<Client> getClientByName(String name){
        return clientRepository.getClientByName(name);
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
    public boolean deleteClient(Long id){
        Optional<Client> client = clientRepository.getClientById(id);
        if (client.isPresent()){
            clientRepository.delete(client.orElse(new Client()));
            return true;
        } else {
            return false;
        }
    }

    public Client saveClient(Client client){
        return clientRepository.save(client);
    }
    @Override
    public Optional<Client> getClientByNameAndPassword(String name, String password) throws NoResultException {
        Optional<Client> client = clientRepository.findByClientNameAndPassword(name, password);
        if(client == null){
            throw new NoResultException("Nome de usuário ou senha incorreta.");
        }
        return client;
    }
}
