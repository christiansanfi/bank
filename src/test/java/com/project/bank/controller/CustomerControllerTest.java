package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.CustomerInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        CustomerInfoDTO customerInfoDTO = CustomerInfoDTO.builder()
                .name("Mario Rossi")
                .birthPlace("Roma")
                .taxCode("MRARSS90E20H501X")
                .address("Via Roma 1")
                .build();

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customerInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mario Rossi"))
                .andExpect(jsonPath("$.address").value("Via Roma 1"));
    }

    @Test
    @Sql(scripts = {"/sql/insert-customer.sql"})
    void getCustomerDetails_ShouldReturnCustomerInfo() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mario Rossi"))
                .andExpect(jsonPath("$.address").value("Via Roma 1"));
    }

    @Test
    @Sql(scripts = {"/sql/insert-customer.sql"})
    void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        CustomerInfoDTO updatedCustomerInfoDTO = CustomerInfoDTO.builder()
                .name("Luigi Verdi")
                .birthPlace("Milano")
                .taxCode("LGVRDI90E20H501X")
                .address("Via Milano 10")
                .build();

        mockMvc.perform(put("/api/customers/{id}", customerId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCustomerInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luigi Verdi"))
                .andExpect(jsonPath("$.address").value("Via Milano 10"));
    }

    @Test
    @Sql(scripts = {"/sql/insert-customer.sql"})
    void deleteCustomer_ShouldReturnNoContent() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCustomerDetails_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        UUID nonExistentCustomerId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        mockMvc.perform(get("/api/customers/{id}", nonExistentCustomerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer with ID: " + nonExistentCustomerId + " not found"));
    }

    @Test
    void createCustomer_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        CustomerInfoDTO invalidCustomerInfo = CustomerInfoDTO.builder().build();

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidCustomerInfo)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("name cannot be null"));
    }

}
