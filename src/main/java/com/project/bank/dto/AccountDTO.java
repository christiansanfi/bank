package com.project.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.bank.model.Account;
import com.project.bank.model.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountDTO {
    private UUID id;
    private UUID customerId;
    private BigDecimal balance;
    private String iban;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.customerId = account.getCustomerId();
        this.balance = account.getBalance();
        this.iban = account.getIban();
    }

    public Account toEntity() {
        Account account = new Account();
        account.setId(this.id);
        account.setCustomerId(this.customerId);
        account.setBalance(this.balance);
        account.setIban(this.iban);
        return account;
    }
}
