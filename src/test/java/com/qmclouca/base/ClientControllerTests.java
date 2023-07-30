package com.qmclouca.base;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qmclouca.base.Dtos.AddressDto;
import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.configs.DisableTestExtension;
import com.qmclouca.base.configs.TestLogAppender;
import com.qmclouca.base.controllers.ClientController;
import com.qmclouca.base.models.Address;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(ClientController.class)
@AutoConfigureJsonTesters
@ExtendWith(DisableTestExtension.class)
public class ClientControllerTests{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;
    private Logger logger = (Logger) LoggerFactory.getLogger(ClientController.class);


    //@DisableTest(reason = "Novos testes")
    @Test
    public void testSaveClient() throws Exception {
        // Prepare the input data for the request body
        ClientDto clientDto = new ClientDto();
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Cidade 1");
        addressDto.setState("Estado 1");
        addressDto.setNumber("1");
        addressDto.setStreet("rua 1");
        addressDto.setReferences("referencias 1");
        addressDto.setPostalCode("Postal code");

        List<AddressDto> lstAddressDto = List.of(addressDto);

        clientDto.setName("John Doe");
        clientDto.setBirthDate(LocalDate.of(1990, 1, 15));
        clientDto.setMobile("1234567890");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(lstAddressDto);

        Client clientResponse = modelMapper.map(clientDto, Client.class);
        Mockito.when(clientService.createClient(Mockito.any(Client.class))).thenReturn(clientResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDto))) // Convert the clientDto to JSON string
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testSomeMethodThatLogs() {
        // Perform some actions that will trigger logging in the application
        logger.info("This is an info log");
        logger.error("This is an error log");

        // Get the custom appender and retrieve the captured logs
        TestLogAppender testAppender = (TestLogAppender) logger.getAppender("TEST");
        List<String> logs = testAppender.getLogs();

        // Assert that the logs contain the expected log messages
        assertEquals(2, logs.size());
        assertEquals("INFO", logs.get(0).substring(24, 28)); // Log level
        assertEquals("This is an info log", logs.get(0).substring(30)); // Log message
        assertEquals("ERROR", logs.get(1).substring(24, 29)); // Log level
        assertEquals("This is an error log", logs.get(1).substring(31)); // Log message
    }

    @Test
    public void testGetClientsByName_ExistingName() throws Exception {

        String name = "John Doe";

        List<Client> mockClients = Collections.singletonList(new Client());
        when(clientService.getClientsByName(name)).thenReturn(mockClients);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}]"));
    }

    @Test
    public void testGetClientsByName_NonExistingName() throws Exception {
        String name = "NonExistingName";

        when(clientService.getClientsByName(name)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Nenhum cliente encontrado com o nome fornecido"));
     }

    @Test
    public void testGetAllClients() throws Exception {
        List<Client> lstClients = new ArrayList<>();
        List<Address> lstAddress1 = new ArrayList<>();
        List<Address> lstAddress2 = new ArrayList<>();
        Client client1 = new Client();
        Client client2 = new Client();
        Address address1 = new Address();
        Address address2 = new Address();
        Address address3 = new Address();
        Address address4 = new Address();
        address1.setCity("Cidade 1");
        address1.setState("Estado 1");
        address1.setNumber("1");
        address1.setStreet("rua 1");
        address1.setReferences("referencias 1");
        address1.setPostalCode("Postal code 1");
        lstAddress1.add(address1);
        address2.setCity("Cidade 2");
        address2.setState("Estado 2");
        address2.setNumber("2");
        address2.setStreet("rua 2");
        address2.setReferences("referencias 2");
        address2.setPostalCode("Postal code 2");
        lstAddress1.add(address2);
        client1.setAddress(lstAddress1);
        client1.setName("John Doe");
        client1.setBirthDate(LocalDate.of(1990, 1, 15));
        client1.setMobile("1234567890");
        client1.setEmail("john.doe@example.com");
        lstClients.add(client1);
        address3.setCity("Cidade 3");
        address3.setState("Estado 3");
        address3.setNumber("3");
        address3.setStreet("rua 3");
        address3.setReferences("referencias 3");
        address3.setPostalCode("Postal code 3");
        lstAddress2.add(address3);
        address4.setCity("Cidade 4");
        address4.setState("Estado 4");
        address4.setNumber("4");
        address4.setStreet("rua 4");
        address4.setReferences("referencias 4");
        address4.setPostalCode("Postal code 4");
        lstAddress2.add(address4);
        client2.setAddress(lstAddress2);
        client2.setName("Abelardo Barbosa");
        client2.setBirthDate(LocalDate.of(1930, 1, 15));
        client2.setMobile("0987687654321");
        client2.setEmail("abelardo.barbosa@example.com");
        lstClients.add(client2);
        Mockito.when(clientService.getAllClients()).thenReturn(lstClients);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthDate").value("1990-01-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobile").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].city").value("Cidade 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].street").value("rua 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].state").value("Estado 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].postalCode").value("Postal code 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].number").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].references").value("referencias 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].city").value("Cidade 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].street").value("rua 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].state").value("Estado 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].postalCode").value("Postal code 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].number").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address[1].references").value("referencias 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Abelardo Barbosa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthDate").value("1930-01-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mobile").value("0987687654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("abelardo.barbosa@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].city").value("Cidade 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].street").value("rua 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].state").value("Estado 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].postalCode").value("Postal code 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].number").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[0].references").value("referencias 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].city").value("Cidade 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].street").value("rua 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].state").value("Estado 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].postalCode").value("Postal code 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].number").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address[1].references").value("referencias 4"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("Response Content: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteClientByName() throws Exception {
        Mockito.when(clientService.deleteClient(Mockito.anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/deleteByName/{name}", "John Doe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testDeleteClientById() throws Exception {
        Mockito.when(clientService.deleteClient(Mockito.anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/deleteById/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testUpdateClientByName() throws Exception {
        ClientDto updatedClientDto = new ClientDto();
        updatedClientDto.setName("Updated John Doe");
        updatedClientDto.setBirthDate(LocalDate.of(1995, 5, 20));
        updatedClientDto.setMobile("9876543210");
        updatedClientDto.setEmail("updated.john.doe@example.com");

        Client existingClient = new Client();
        existingClient.setName("John Doe");
        existingClient.setBirthDate(LocalDate.of(1990, 1, 15));
        existingClient.setMobile("1234567890");
        existingClient.setEmail("john.doe@example.com");
        Mockito.when(clientService.getClientByName(Mockito.anyString())).thenReturn(Optional.of(existingClient));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clients/{name}", "John Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedClientDto))) // Convert the updatedClientDto to JSON string
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Client updated successfully"));
    }


    private static String asJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}