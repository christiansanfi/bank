package com.project.bank.mapper;

import com.project.bank.dto.GetTransactionDTO;
import com.project.bank.dto.TransactionDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.model.Transaction;

public class TransactionMapper {

    public TransactionDTO fromTransactionToTransactionDto (Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAccountId(transaction.getId());
        transactionDTO.setAmount(transactionDTO.getAmount());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setDate(transaction.getDate());
        return transactionDTO;
    }

    public TransactionRequestDTO fromTransactionToTransactionRequest (Transaction transaction){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAccountId(transaction.getId());
        transactionRequestDTO.setAmount(transaction.getAmount());
        return transactionRequestDTO;
    }

    public GetTransactionDTO fromTransactionToGetTransactionDto (Transaction transaction){
        GetTransactionDTO getTransactionDTO = new GetTransactionDTO();
        getTransactionDTO.setAmount(transaction.getAmount());
        getTransactionDTO.setType(transaction.getType());
        getTransactionDTO.setDate(transaction.getDate());
        return getTransactionDTO;
    }
}
