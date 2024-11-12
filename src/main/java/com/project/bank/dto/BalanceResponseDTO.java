package com.project.bank.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class BalanceResponseDTO {
    private BigDecimal balance;
}
