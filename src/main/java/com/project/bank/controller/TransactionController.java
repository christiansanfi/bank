package com.project.bank.controller;

import com.project.bank.dto.GetTransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
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
    public ResponseEntity<GetTransactionResponseDTO> deposit(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        GetTransactionResponseDTO transactionResponseDTO = transactionService.deposit(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<GetTransactionResponseDTO> withdraw(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        GetTransactionResponseDTO transactionResponseDTO = transactionService.withdraw(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/last-five/{accountId}")
    public ResponseEntity<List<GetTransactionResponseDTO>> getLastFiveTransactions(@PathVariable UUID accountId){
        List<GetTransactionResponseDTO> response = transactionService.getLastFiveTransactions(accountId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id")UUID id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }


}
