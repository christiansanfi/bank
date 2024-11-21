package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionRequestDTO {
    private UUID accountId;
    @Positive(message = "Amount must be higher than 0")
    private BigDecimal amount;
}
