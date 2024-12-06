package com.project.bank.controller;

import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.model.TransactionType;
import com.project.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.deposit(transactionRequestDTO, TransactionType.DEPOSIT);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdraw(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.withdraw(transactionRequestDTO, TransactionType.DEPOSIT);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/last-five/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getLastFiveTransactions(@PathVariable UUID accountId) {
        List<TransactionResponseDTO> response = transactionService.getLastFiveTransactions(accountId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
