package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.exception.InsufficientBalanceException;
import com.project.bank.exception.NegativeAmountException;
import com.project.bank.exception.TransactionNotFoundException;
import com.project.bank.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deposit_ShouldReturnTransactionResponseDTO() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(UUID.randomUUID(), BigDecimal.valueOf(100.0));
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        when(transactionService.deposit(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deposit_ShouldReturnBadRequest_WhenAmountIsNegative() throws Exception {
        // Arrange
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(UUID.randomUUID(), BigDecimal.valueOf(-100.0));

        // Act & Assert
        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Amount must be higher than 0"));
    }

    @Test
    void withdraw_ShouldReturnTransactionResponseDTO() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(UUID.randomUUID(),BigDecimal.valueOf(100.0));
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        when(transactionService.withdraw(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void withdraw_ShouldReturnBadRequest_WhenBalanceIsInsufficient() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(UUID.randomUUID(), BigDecimal.valueOf(1000.0));

        when(transactionService.withdraw(requestDTO)).thenThrow(new InsufficientBalanceException("Insufficient balance"));

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient balance"));
    }


    @Test
    void getLastFiveTransactions_ShouldReturnTransactionList() throws Exception {
        UUID accountId = UUID.randomUUID();
        List<TransactionResponseDTO> responseList = List.of(new TransactionResponseDTO(), new TransactionResponseDTO());

        when(transactionService.getLastFiveTransactions(accountId)).thenReturn(responseList);

        mockMvc.perform(get("/api/transactions/last-five/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseList)));
    }

    @Test
    void getLastFiveTransactions_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();

        when(transactionService.getLastFiveTransactions(accountId)).thenThrow(new TransactionNotFoundException("Transactions not found"));

        mockMvc.perform(get("/api/transactions/last-five/{accountId}", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Transactions not found"));
    }

    @Test
    void deleteTransaction_ShouldReturnNoContent() throws Exception {
        UUID transactionId = UUID.randomUUID();
        doNothing().when(transactionService).deleteTransaction(transactionId);

        mockMvc.perform(delete("/api/transactions/{id}", transactionId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTransaction_ShouldReturnNotFound_WhenTransactionDoesNotExist() throws Exception {
        UUID transactionId = UUID.randomUUID();

        doThrow(new TransactionNotFoundException("Transaction not found")).when(transactionService).deleteTransaction(transactionId);

        mockMvc.perform(delete("/api/transactions/{id}", transactionId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Transaction not found"));
    }
}
