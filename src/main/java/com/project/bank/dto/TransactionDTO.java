package com.project.bank.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.bank.model.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransactionDTO {
    private UUID id;
    private UUID accountId;
    private float amount;
    private String type;
    private LocalDateTime date;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.accountId = transaction.getAccountId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.date = transaction.getDate();
    }

    public Transaction toEntity(){
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setAccountId(this.accountId);
        transaction.setAmount(this.amount);
        transaction.setType(this.type);
        transaction.setDate(this.date);
        return transaction;
    }
}
