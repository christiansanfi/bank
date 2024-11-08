package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class TransactionRequestDTO {
    private UUID accountId;
    private BigDecimal amount;
}
