package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.TransactionRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert-customer.sql", "/sql/insert-account.sql", "/sql/insert-transaction.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deposit_ShouldReturnTransactionResponse() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(100.00)
        );

        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void withdraw_ShouldReturnTransactionResponse() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(50.00)
        );

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getLastFiveTransactions_ShouldReturnTransactions() throws Exception {
        UUID accountId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/transactions/last-five/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deleteTransaction_ShouldReturnNoContent() throws Exception {
        UUID transactionId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/transactions/{id}", transactionId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getLastFiveTransactions_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID invalidAccountId = UUID.fromString("111e4567-e89b-12d3-a456-426614174999");

        mockMvc.perform(get("/api/transactions/last-five/{accountId}", invalidAccountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Transactions not found for account id: " + invalidAccountId));
    }

    @Test
    void withdraw_ShouldReturnBadRequest_WhenBalanceIsInsufficient() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(10000.00) // Importo superiore al saldo disponibile
        );

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Balance is not sufficient to cover the withdraw"));
    }


}
