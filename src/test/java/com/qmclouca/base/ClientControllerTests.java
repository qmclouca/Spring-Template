package com.qmclouca.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qmclouca.base.Dtos.AddressDto;
import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.configs.DisableTestExtension;
import com.qmclouca.base.controllers.ClientController;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.services.ClientService;
import com.qmclouca.base.utils.annotations.DisableTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
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
    private ClientService clientService; // Assuming you have a ClientService to mock

    //@DisableTest(reason = "Testando novos testes")
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDto))) // Convert the clientDto to JSON string
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
        // Add more assertions based on your response data and expected behavior
    }

    @Test
    public void testGetClientsByName_ExistingName() throws Exception {
        // Prepare the input name for the request path
        String name = "John Doe";

        // Mock the behavior of the clientService
        // Return a list of mock clients for the given name
        List<Client> mockClients = Collections.singletonList(new Client());
        when(clientService.getClientsByName(name)).thenReturn(mockClients);

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}]")); // Expecting a JSON array with one object
        // Add more assertions based on your response data and expected behavior
    }

    @Test
    public void testGetClientsByName_NonExistingName() throws Exception {
        // Prepare the input name for the request path
        String name = "NonExistingName";

        // Mock the behavior of the clientService
        // Return an empty list to simulate that no client with the given name exists
        when(clientService.getClientsByName(name)).thenReturn(Collections.emptyList());

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Nenhum cliente encontrado com o nome fornecido"));
        // Add more assertions based on your response data and expected behavior
    }

    @DisableTest(reason = "Testando novos testes")
    @Test
    public void testGetClientsByName_NullOrEmptyName() throws Exception {
        // Prepare the input name for the request path (null or empty)
        String nullName = null;
        String emptyName = "";

        // Perform the request and validate the response for null name
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + nullName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("Nenhum cliente encontrado com o nome fornecido"));

        // Perform the request and validate the response for empty name
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + emptyName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("Nenhum cliente encontrado com o nome fornecido"));
    }

    // Helper method to convert objects to JSON strings
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