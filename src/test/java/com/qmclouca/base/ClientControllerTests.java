package com.qmclouca.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qmclouca.base.Dtos.AddressDto;
import com.qmclouca.base.Dtos.ClientDto;
import com.qmclouca.base.controllers.ClientController;
import com.qmclouca.base.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Collections;

@WebMvcTest(ClientController.class)
@AutoConfigureJsonTesters
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService; // Assuming you have a ClientService to mock

    @Test
    public void testSaveClient() throws Exception {
        // Prepare the input data for the request body
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John Doe");
        clientDto.setBirthDate(LocalDate.of(1990, 1, 15));
        clientDto.setMobile("1234567890");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(Collections.singletonList(new AddressDto()));

        // Mock the behavior of the clientService
        // You should mock the necessary methods based on your test scenarios
        // For example, you can use Mockito.when() and thenReturn() to mock the service behavior

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDto))) // Convert the clientDto to JSON string
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
        // Add more assertions based on your response data and expected behavior
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