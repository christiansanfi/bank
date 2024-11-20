package com.project.bank.controller;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.dto.CreateAccountRequestDTO;
import com.project.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        UUID id = createAccountRequestDTO.getCustomerId();
        AccountResponseDTO accountResponse = accountService.createAccount(id);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable("id") UUID id) {
        BalanceResponseDTO balanceResponseDTO = accountService.getBalance(id);
        return ResponseEntity.ok(balanceResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountFromId(@PathVariable("id") UUID id) {
        AccountResponseDTO accountResponseDto = accountService.getAccountFromId(id);
        return ResponseEntity.ok(accountResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
