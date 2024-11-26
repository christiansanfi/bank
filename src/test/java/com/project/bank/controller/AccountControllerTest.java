package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.dto.CreateAccountRequestDTO;
import com.project.bank.exception.AccountNotFoundException;
import com.project.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount_ShouldReturnCreatedAccount() throws Exception {
        UUID customerId = UUID.randomUUID();
        CreateAccountRequestDTO createAccountRequestDTO = new CreateAccountRequestDTO(customerId);
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

        when(accountService.createAccount(customerId)).thenReturn(accountResponseDTO);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(accountResponseDTO)));
    }

    @Test
    void getBalance_ShouldReturnBalance() throws Exception {
        UUID accountId = UUID.randomUUID();
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO(BigDecimal.valueOf(1000.0));

        when(accountService.getBalance(accountId)).thenReturn(balanceResponseDTO);

        mockMvc.perform(get("/api/accounts/{id}/balance", accountId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceResponseDTO)));
    }

    @Test
    void getBalance_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();

        when(accountService.getBalance(accountId)).thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(get("/api/accounts/{id}/balance", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found"));
    }

    @Test
    void getAccountFromId_ShouldReturnAccountDetails() throws Exception {
        UUID accountId = UUID.randomUUID();
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

        when(accountService.getAccountFromId(accountId)).thenReturn(accountResponseDTO);

        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountResponseDTO)));
    }

    @Test
    void getAccountFromId_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();

        when(accountService.getAccountFromId(accountId)).thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found"));
    }

    @Test
    void deleteAccount_ShouldReturnNoContent() throws Exception {
        UUID accountId = UUID.randomUUID();
        doNothing().when(accountService).deleteAccount(accountId);

        mockMvc.perform(delete("/api/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAccount_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();

        doThrow(new AccountNotFoundException("Account not found")).when(accountService).deleteAccount(accountId);

        mockMvc.perform(delete("/api/accounts/{id}", accountId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found"));
    }
}
