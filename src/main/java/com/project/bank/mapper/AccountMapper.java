package com.project.bank.mapper;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.AccountIdRequestDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.model.Account;
import com.project.bank.model.Customer;
import com.project.bank.service.AccountService;
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

    public Account fromCustomerToAccount(Customer customer){
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);
        account.setIban(generateRandomIban());
        return account;
    }

    private String generateRandomIban(){
        return "IT" + (int)(Math.random() * 89 + 10) + "X0542811101000000" + (int)(Math.random() * 8999999 + 1000000);
    }

    public BigDecimal fromBalanceDtoToBalance(BalanceResponseDTO balanceDTO){
        return balanceDTO.getBalance();
    }
}

