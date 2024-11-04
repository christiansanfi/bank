package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AccountResponseDTO {
    private UUID id;
    private UUID customerId;
    private BigDecimal balance;
    private String iban;
}
