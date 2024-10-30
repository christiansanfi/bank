package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.bank.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BalanceDTO {
    private BigDecimal balance;

    public BalanceDTO(Account account) {
        this.balance = account.getBalance();
    }

    public Account toEntity() {
        Account account = new Account();
        account.setBalance(this.balance);
        return account;
    }
}
