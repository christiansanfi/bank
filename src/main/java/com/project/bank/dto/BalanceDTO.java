package com.project.bank.dto;

import java.math.BigDecimal;

import com.project.bank.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class BalanceDTO {
    private BigDecimal balance;
}
