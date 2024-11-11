package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class TransactionRequestDTO {
    private UUID accountId;
    @Positive(message = "Amount must be higher than 0")
    private BigDecimal amount;
}
