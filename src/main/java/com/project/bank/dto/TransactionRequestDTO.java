package com.project.bank.dto;

import java.util.UUID;

import com.project.bank.model.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransactionRequestDTO {
    private UUID accountId;
    private float amount;

    public TransactionRequestDTO(Transaction transaction){
        this.accountId = transaction.getAccountId();
        this.amount = transaction.getAmount();
    }

    public Transaction toEntity(){
        Transaction transaction = new Transaction();
        transaction.setAccountId(this.accountId);
        transaction.setAmount(this.amount);
        return transaction;
    }
}
