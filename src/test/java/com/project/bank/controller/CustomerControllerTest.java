package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/sql/cleanup.sql"})
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        String requestBody = """
                {
                    "name": "Mario Rossi",
                    "birthPlace": "Roma",
                    "taxCode": "MRARSS90E20H501X",
                    "address": "Via Roma 1"
                }
                """;

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mario Rossi"))
                .andExpect(jsonPath("$.address").value("Via Roma 1"));
    }

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql"})
    void getCustomerDetails_ShouldReturnCustomerInfo() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mario Rossi"))
                .andExpect(jsonPath("$.address").value("Via Roma 1"));
    }

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql"})
    void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String requestBody = """
                {
                    "name": "Luigi Verdi",
                    "birthPlace": "Milano",
                    "taxCode": "LGVRDI90E20H501X",
                    "address": "Via Milano 10"
                }
                """;

        mockMvc.perform(put("/api/customers/{id}", customerId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luigi Verdi"))
                .andExpect(jsonPath("$.address").value("Via Milano 10"));
    }

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql"})
    void deleteCustomer_ShouldReturnNoContent() throws Exception {
        UUID customerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNoContent());
    }
}
