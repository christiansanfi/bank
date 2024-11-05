package com.project.bank.mapper;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.AccountIdRequestDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountMapper {

    public AccountResponseDTO fromAccountToAccountDTO(Account account) {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setCustomerId(account.getCustomer().getId());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setIban(account.getIban());
        return accountResponseDTO;
    }

    public AccountIdRequestDTO fromIdToAccountIdDTO (UUID id){
        AccountIdRequestDTO accountIdDTO = new AccountIdRequestDTO();
        accountIdDTO.setId(id);
        return accountIdDTO;
    }

    public UUID fromAccountIdRequestDtoToId (AccountIdRequestDTO accountIdRequestDTO){
        return accountIdRequestDTO.getId();
    }

    public BalanceResponseDTO fromBalanceToBalanceDTO(BigDecimal balance){
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        balanceResponseDTO.setBalance(balance);
        return balanceResponseDTO;
    }

    public BigDecimal fromBalanceDtoToBalance(BalanceResponseDTO balanceDTO){
        return balanceDTO.getBalance();
    }
}

