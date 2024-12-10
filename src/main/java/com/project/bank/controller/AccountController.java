package com.project.bank.controller;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.dto.CreateAccountRequestDTO;
import com.project.bank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "Endpoints for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new account", description = "Create a new bank account for a customer")
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        UUID id = createAccountRequestDTO.getCustomerId();
        AccountResponseDTO accountResponse = accountService.createAccount(id);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get account balance", description = "Retrieve the balance of a specific account")
    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable("id") UUID id) {
        BalanceResponseDTO balanceResponseDTO = accountService.getBalance(id);
        return ResponseEntity.ok(balanceResponseDTO);
    }

    @Operation(summary = "Get account details", description = "Retrieve the details of a specific account")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountFromId(@PathVariable("id") UUID id) {
        AccountResponseDTO accountResponseDto = accountService.getAccountFromId(id);
        return ResponseEntity.ok(accountResponseDto);
    }

    @Operation(summary = "Delete an account", description = "Delete a specific account by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
