package com.project.bank.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransactionDTO {
    private UUID transactionId;
    private UUID accountId;
    private float amount;
    private String type;
    private LocalDateTime date;
}
