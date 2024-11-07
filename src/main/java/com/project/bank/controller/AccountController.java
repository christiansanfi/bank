package com.project.bank.controller;

import com.project.bank.dto.AccountIdRequestDTO;
import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CustomerIdRequestDTO customerIdRequestDTO) {
        AccountResponseDTO accountResponse = accountService.createAccount(customerIdRequestDTO);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable("id") UUID id){
        BalanceResponseDTO balanceResponseDTO = accountService.getBalance(id);
        return ResponseEntity.ok(balanceResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") UUID id ){
        AccountIdRequestDTO accountIdRequestDTO = new AccountIdRequestDTO();
        accountIdRequestDTO.setId(id);
        accountService.deleteAccount(accountIdRequestDTO);
        return ResponseEntity.noContent().build();
    }


}
