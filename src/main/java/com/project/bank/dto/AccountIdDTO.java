package com.project.bank.dto;

import java.util.UUID;

import com.project.bank.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountIdDTO {
    private UUID id;

    public AccountIdDTO(Account account) {
        this.id = account.getId();
    }

    public Account toEntity() {
        Account account = new Account();
        account.setId(this.id);
        return account;
    }
}
