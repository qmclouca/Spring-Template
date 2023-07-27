package com.qmclouca.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qmclouca.base.Dtos.AddressDto;
import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.configs.DisableTestExtension;
import com.qmclouca.base.controllers.ClientController;
import com.qmclouca.base.models.Address;
import com.qmclouca.base.models.Client;
import com.qmclouca.base.services.ClientService;
import com.qmclouca.base.utils.annotations.DisableTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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

        Client clientResponse = modelMapper.map(clientDto, Client.class);
        Mockito.when(clientService.createClient(Mockito.any(Client.class))).thenReturn(clientResponse);

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

    @Test
    public void testGetAllClients() throws Exception {

        List<Client> lstClients = new ArrayList<>();
        List<Address> lstAddress = new ArrayList<>();

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
        lstAddress.add(address1);
        address2.setCity("Cidade 2");
        address2.setState("Estado 2");
        address2.setNumber("2");
        address2.setStreet("rua 2");
        address2.setReferences("referencias 2");
        address2.setPostalCode("Postal code 2");
        lstAddress.add(address2);
        client1.setAddress(lstAddress);
        client1.setName("John Doe");
        client1.setBirthDate(LocalDate.of(1990, 1, 15));
        client1.setMobile("1234567890");
        client1.setEmail("john.doe@example.com");
        lstClients.add(client1);

        lstAddress.clear();

        address3.setCity("Cidade 3");
        address3.setState("Estado 3");
        address3.setNumber("3");
        address3.setStreet("rua 3");
        address3.setReferences("referencias 3");
        address3.setPostalCode("Postal code 3");
        lstAddress.add(address3);

        address4.setCity("Cidade 4");
        address4.setState("Estado 4");
        address4.setNumber("4");
        address4.setStreet("rua 4");
        address4.setReferences("referencias 4");
        address4.setPostalCode("Postal code 4");
        lstAddress.add(address4);

        client2.setAddress(lstAddress);
        client2.setName("Abelardo Barbosa");
        client2.setBirthDate(LocalDate.of(1930, 1, 15));
        client2.setMobile("0987687654321");
        client2.setEmail("abelardo.barbosa@example.com");
        lstClients.add(client2);

        Mockito.when(clientService.getAllClients()).thenReturn(lstClients);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$[0].address[0].city").value("Cidade 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Abelardo Barbosa"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("Response Content: " + result.getResponse().getContentAsString());
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