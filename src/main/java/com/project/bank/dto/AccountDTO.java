package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountDTO {
    private UUID accountId;
    private UUID customerId;
    private BigDecimal balance;
    private String iban;
}
