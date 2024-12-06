package com.project.bank.mapper;

import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import com.project.bank.model.TransactionType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransactionMapper {

    public TransactionResponseDTO fromTransactionToGetTransactionResponseDto(Transaction transaction) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setAmount(transaction.getAmount());
        transactionResponseDTO.setType(transaction.getType());
        transactionResponseDTO.setDate(transaction.getDate());
        return transactionResponseDTO;
    }

    public Transaction fromTransactionRequestDTOToTransactionResponseDTO(TransactionRequestDTO transactionRequestDTO, TransactionType type, Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(transactionRequestDTO.getAmount());
        return transaction;
    }
}
