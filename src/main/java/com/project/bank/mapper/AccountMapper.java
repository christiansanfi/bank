package com.project.bank.mapper;

import com.project.bank.dto.AccountDTO;
import com.project.bank.dto.AccountIdDTO;
import com.project.bank.dto.BalanceDTO;
import com.project.bank.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountMapper {

    public AccountDTO fromAccountToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setCustomerId(account.getCustomer().getId());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setIban(account.getIban());
        return accountDTO;
    }

    public AccountIdDTO fromIdToAccountIdDTO (UUID id){
        AccountIdDTO accountIdDTO = new AccountIdDTO();
        accountIdDTO.setId(id);
        return accountIdDTO;
    }

    public UUID fromAccountIdDtoToId (AccountIdDTO accountIdDTO){
        return accountIdDTO.getId();
    }

    public BalanceDTO fromBalanceToBalanceDTO(BigDecimal balance){
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(balance);
        return balanceDTO;
    }

    public BigDecimal fromBalanceDtoToBalance(BalanceDTO balanceDTO){
        return balanceDTO.getBalance();
    }
}

