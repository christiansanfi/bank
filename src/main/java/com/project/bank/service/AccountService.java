
package com.project.bank.service;

import com.project.bank.dto.*;
import com.project.bank.exception.AccountNotFoundException;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.mapper.CustomerMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Customer;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;
    public final AccountMapper accountMapper;
    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    public AccountResponseDTO createAccount(CustomerIdRequestDTO customerIdRequestDTO) {
        UUID id = customerMapper.fromCustomerIdRequestDtoToCustomerId(customerIdRequestDTO);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID: " + id + " not found"));
        Account savedAccount = accountRepository.save(accountMapper.fromCustomerToAccount(customer, generateRandomIban()));
        return accountMapper.fromAccountToAccountDTO(savedAccount);
    }

    public BalanceResponseDTO getBalance(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " not found"));
        return accountMapper.fromBalanceToBalanceDTO(account.getBalance());
    }

    public AccountResponseDTO getAccountFromId(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));
        return accountMapper.fromAccountToAccountResponseDTO(account);
    }

    public void deleteAccount(UUID id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account with id " + id + " not found");
        }
    }

    private String generateRandomIban() {
        return "IT" + (int) (Math.random() * 89 + 10) + "X0542811101000000" + (int) (Math.random() * 8999999 + 1000000);
    }
}

