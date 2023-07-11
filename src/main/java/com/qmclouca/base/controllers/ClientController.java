package com.qmclouca.base.controllers;

import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ModelMapper modelMapper;

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        super();
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto postClientDto){

        Client clientRequest = modelMapper.map(postClientDto, Client.class);
        Client client = clientService.createClient(clientRequest);

        ClientDto postClientResponse = modelMapper.map(client, ClientDto.class);

        if (postClientResponse != null) {
            return ResponseEntity.ok(postClientResponse); // Return the client with 200 OK status
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return 400 Bad Request status
        }
    }
    /*
    @Operation(summary = "Atualiza um cliente existente")
    @GetMapping("/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> getClienteByNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio");
        }

        Client client = clientService.findClientsByName(nome);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Retorna um cliente pelo seu id")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<Client>> getClienteById(@PathVariable Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("O id do cliente não pode ser nulo");
        }
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @Operation(summary="Deleta um cliente pelo seu id")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientRepository.deleteById(id);
    }

     */
}
