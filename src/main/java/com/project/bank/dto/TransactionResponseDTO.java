package com.project.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.project.bank.model.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponseDTO {
    private BigDecimal amount;
    private Transaction.Type type;
    private LocalDateTime date;
}
