package com.project.bank.controller;

import com.project.bank.dto.GetTransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<GetTransactionResponseDTO> deposit(@RequestBody TransactionRequestDTO transactionRequestDTO){
        GetTransactionResponseDTO transactionResponseDTO = transactionService.deposit(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<GetTransactionResponseDTO> withdraw(@RequestBody TransactionRequestDTO transactionRequestDTO){
        GetTransactionResponseDTO transactionResponseDTO = transactionService.withdraw(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id")UUID id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }


}
