package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.CreateAccountRequestDTO;
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
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount_ShouldReturnCreatedAccount() throws Exception {
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        mockMvc.perform(post("/api/accounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getBalance_ShouldReturnBalance() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        mockMvc.perform(get("/api/accounts/{id}/balance", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("1500.0"));
    }

    @Test
    void getAccountFromId_ShouldReturnAccountDetails() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value("IT60X0542811101000000123456"));
    }

    @Test
    void deleteAccount_ShouldReturnNoContent() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        mockMvc.perform(delete("/api/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAccountFromId_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID invalidAccountId = UUID.fromString("111e4567-e89b-12d3-a456-426614174999");

        mockMvc.perform(get("/api/accounts/{id}", invalidAccountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account with id " + invalidAccountId + " not found"));
    }

}
