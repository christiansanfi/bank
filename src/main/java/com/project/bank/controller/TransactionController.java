package com.project.bank.controller;

import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "Endpoints for handling transactions like deposit and withdrawal")
public class TransactionController {

    private final TransactionService transactionService;
    @Operation(summary = "Deposit funds", description = "Deposit money into a specific account")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.deposit(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Withdraw funds", description = "Withdraw money from a specific account")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdraw(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.withdraw(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get last five transactions", description = "Retrieve the last five transactions for a specific account")
    @GetMapping("/last-five/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getLastFiveTransactions(@PathVariable UUID accountId) {
        List<TransactionResponseDTO> response = transactionService.getLastFiveTransactions(accountId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a transaction", description = "Delete a specific transaction by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
