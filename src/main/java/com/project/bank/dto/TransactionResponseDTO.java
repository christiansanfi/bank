package com.project.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class TransactionResponseDTO {
    private UUID id;
    private UUID accountId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime date;
}
