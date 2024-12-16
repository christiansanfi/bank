package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    void makeTransaction_ShouldReturnTransactionResponse_WhenDeposit() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(100.00),
                Transaction.Type.DEPOSIT
        );

        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();
        expectedResponse.setAmount(BigDecimal.valueOf(50.00));
        expectedResponse.setType(Transaction.Type.DEPOSIT);
        expectedResponse.setDate(LocalDateTime.now());

        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.amount").value(expectedResponse.getAmount().doubleValue()))
                .andExpect(jsonPath("$.type").value(expectedResponse.getType().toString()))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    void makeTransaction_ShouldReturnTransactionResponse_WhenWithdraw() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(50.00),
                Transaction.Type.WITHDRAW
        );

        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();
        expectedResponse.setAmount(BigDecimal.valueOf(50.00));
        expectedResponse.setType(Transaction.Type.WITHDRAW);
        expectedResponse.setDate(LocalDateTime.now());

        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.amount").value(expectedResponse.getAmount().doubleValue()))
                .andExpect(jsonPath("$.type").value(expectedResponse.getType().toString()))
                .andExpect(jsonPath("$.date").exists());
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
    void makeTransaction_ShouldReturnBadRequest_WhenBalanceIsInsufficient() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(10000.00),
                Transaction.Type.WITHDRAW
        );

        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Balance is not sufficient"));
    }
}
