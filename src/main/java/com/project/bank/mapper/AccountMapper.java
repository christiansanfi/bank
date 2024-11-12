package com.project.bank.mapper;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.model.Account;
import com.project.bank.model.Customer;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountMapper {

    public AccountResponseDTO fromAccountToAccountDTO(Account account) {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setCustomerId(account.getCustomer().getId());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setIban(account.getIban());
        return accountResponseDTO;
    }

    public BalanceResponseDTO fromBalanceToBalanceDTO(BigDecimal balance) {
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        balanceResponseDTO.setBalance(balance);
        return balanceResponseDTO;
    }

    public Account fromCustomerToAccount(Customer customer, String iban) {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);
        account.setIban(iban);
        return account;
    }

    public AccountResponseDTO fromAccountToAccountResponseDTO(Account account) {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setIban(account.getIban());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setCustomerId(account.getCustomer().getId());
        return accountResponseDTO;
    }

}

