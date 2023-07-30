package com.qmclouca.base.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmclouca.base.Dtos.AddressDto;
import com.qmclouca.base.utils.JwtGenerator.JwtGeneratorInterface;
import jakarta.persistence.NoResultException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ModelMapper modelMapper;

    private final ClientService clientService;

    private JwtGeneratorInterface jwtGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public ClientController(ClientService clientService, JwtGeneratorInterface jwtGenerator){
        super();
        this.clientService = clientService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody Client client) {
        try {
            if(client.getClientName() == null || client.getPassword() == null) {
                throw new NoResultException("UserName or Password is Empty");
            }
            Optional<Client> clientData = clientService.getClientByNameAndPassword(client.getClientName(), client.getPassword());
            if(clientData.isEmpty()){
                throw new NoResultException("UserName or Password is Invalid");
            }
            return new ResponseEntity<>(jwtGenerator.generateToken(client), HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto postClientDto){

        Client clientRequest = modelMapper.map(postClientDto, Client.class);

        if (clientService.getClientsByName(postClientDto.getName()).size() != 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Client client = clientService.createClient(clientRequest);

        ClientDto postClientResponse = modelMapper.map(client, ClientDto.class);

        if (postClientResponse != null) {
            return ResponseEntity.ok(postClientResponse); // Return the client with 200 OK status
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return 400 Bad Request status
        }
    }

    @GetMapping("{name}")
    public ResponseEntity<String> getClientsByName(@PathVariable String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O nome do cliente não pode ser nulo ou vazio");
        }

        List<Client> clients = clientService.getClientsByName(name);
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente encontrado com o nome fornecido");
        }

        String json;
        try {
            json = objectMapper.writeValueAsString(clients);
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar o JSON");
        }
    }

    @GetMapping
    public ResponseEntity<String> getAllClients(){
        List<Client> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente encontrado.");
        }
        List<ClientDto> clientDtos = clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        String json;
        try {
            json = objectMapper.writeValueAsString(clientDtos);
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar o JSON");
        }
    }

    @DeleteMapping("deleteByName/{name}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable String name){
        boolean isDeleted =  clientService.deleteClient(name);
        return ResponseEntity.ok(isDeleted);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id){
        boolean isDeleted = clientService.deleteClient(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateClientByName(@PathVariable String name, @RequestBody ClientDto updatedClientDto) {
        Optional<Client> client = clientService.getClientByName(name);

        if (client.isPresent()) {
            Client existingClient = client.get();
            existingClient.setName(updatedClientDto.getName());
            existingClient.setBirthDate(updatedClientDto.getBirthDate());
            existingClient.setMobile(updatedClientDto.getMobile());
            existingClient.setEmail(updatedClientDto.getEmail());

            clientService.saveClient(existingClient);

            return ResponseEntity.ok("Client updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ClientDto convertToDto(Client client) {
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);

        List<AddressDto> addressDtos = client.getAddress().stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
        clientDto.setAddress(addressDtos);
        return clientDto;
    }
}
