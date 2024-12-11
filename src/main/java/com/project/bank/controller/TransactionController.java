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

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> makeTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO, @RequestParam("type") TransactionType type) {
        TransactionResponseDTO transactionResponseDTO = transactionService.makeTransaction(transactionRequestDTO, type);
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
