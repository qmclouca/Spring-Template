package com.qmclouca.base.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
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

    @Autowired
    private ObjectMapper objectMapper;

    public ClientController(ClientService clientService){
        super();
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto postClientDto){

        Client clientRequest = modelMapper.map(postClientDto, Client.class);
        if (clientService.getClientsByName(clientRequest.getName()) != null){
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
            return ResponseEntity.badRequest().body("O nome do cliente não pode ser nulo ou vazio");
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

    private ClientDto convertToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        return clientDto;
    }



}
