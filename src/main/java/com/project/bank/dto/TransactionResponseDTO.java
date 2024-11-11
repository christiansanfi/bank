package com.project.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponseDTO {
    private BigDecimal amount;
    private String type;
    private LocalDateTime date;
}
