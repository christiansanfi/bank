
package com.project.bank.service;

import com.project.bank.dto.*;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.mapper.CustomerMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Customer;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    public final AccountRepository accountRepository;
    public final AccountMapper accountMapper;
    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public  AccountResponseDTO createAccount(CustomerIdRequestDTO customerIdRequestDTO){
        UUID id = customerMapper.fromCustomerIdRequestDtoToCustomerId(customerIdRequestDTO);
        Optional<Customer> customer = customerRepository.findById(id);
        //To verify if the customer exist, ##ADD AN EXCEPTION
        if(customer.isEmpty()){
            throw new IllegalArgumentException("Customer not found!");
        }

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer.get());
        account.setIban(generateRandomIban());
        Account savedAccount = accountRepository.save(account);

        return accountMapper.fromAccountToAccountDTO(savedAccount);
    }

    public BalanceResponseDTO getBalance(AccountIdRequestDTO accountIdRequestDTO){
        UUID id = accountMapper.fromAccountIdRequestDtoToId(accountIdRequestDTO);
        Account account = accountRepository.getReferenceById(id);
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        balanceResponseDTO.setBalance(account.getBalance());
        return balanceResponseDTO;
    }


    public void deleteAccount(AccountIdRequestDTO accountIdRequestDTO){
        UUID id = accountMapper.fromAccountIdRequestDtoToId(accountIdRequestDTO);
        if(accountRepository.existsById(id)){
            accountRepository.deleteById(id);
        } else{
            throw new IllegalArgumentException("Account not found");
        }
    }


    private String generateRandomIban(){
        return "IT" + (int)(Math.random() * 89 + 10) + "X0542811101000000" + (int)(Math.random() * 8999999 + 1000000);
    }

}

