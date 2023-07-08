package com.qmclouca.base.controllers;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RestController
@ComponentScan
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Operation(summary = "Criar um novo cliente.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody Client client){
        return clientRepository.save(client);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> getClienteByNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio");
        }

        Client client = clientRepository.findClientsByLastName(nome);
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
}
