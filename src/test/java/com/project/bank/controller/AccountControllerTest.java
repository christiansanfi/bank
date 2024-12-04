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
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createAccount_ShouldReturnCreatedAccount() throws Exception {
        String requestBody = """
            {
                "customerId": "123e4567-e89b-12d3-a456-426614174000"
            }
            """;

        mockMvc.perform(post("/api/accounts")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }



    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql"})
    void getBalance_ShouldReturnBalance() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/accounts/{id}/balance", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("1500.0"));
    }

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql"})
    void getAccountFromId_ShouldReturnAccountDetails() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value("IT60X0542811101000000123456"));
    }

    @Test
    @Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql"})
    void deleteAccount_ShouldReturnNoContent() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }
}
