package com.project.bank.mapper;

import com.project.bank.dto.GetTransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransactionMapper {

    public TransactionResponseDTO fromTransactionToTransactionResponseDto (Transaction transaction){
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setId(transaction.getId());
        transactionResponseDTO.setAccountId(transaction.getAccount().getId());
        transactionResponseDTO.setAmount(transaction.getAmount());
        transactionResponseDTO.setType(transaction.getType());
        transactionResponseDTO.setDate(transaction.getDate());
        return transactionResponseDTO;
    }

    public TransactionRequestDTO fromTransactionToTransactionRequest (Transaction transaction){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAccountId(transaction.getId());
        transactionRequestDTO.setAmount(transaction.getAmount());
        return transactionRequestDTO;
    }

    public GetTransactionResponseDTO fromTransactionToGetTransactionResponseDto (Transaction transaction){
        GetTransactionResponseDTO getTransactionResponseDTO = new GetTransactionResponseDTO();
        getTransactionResponseDTO.setAmount(transaction.getAmount());
        getTransactionResponseDTO.setType(transaction.getType());
        getTransactionResponseDTO.setDate(transaction.getDate());
        return getTransactionResponseDTO;
    }

    public Transaction fromTransactionRequestDTOToTransactionResponseDTO(TransactionRequestDTO transactionRequestDTO, String type, Account account){
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(transactionRequestDTO.getAmount());
        return transaction;
    }
}
