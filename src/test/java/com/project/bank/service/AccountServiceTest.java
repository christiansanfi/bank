package com.project.bank.service;

import com.project.bank.dto.AccountResponseDTO;
import com.project.bank.dto.BalanceResponseDTO;
import com.project.bank.exception.AccountNotFoundException;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Customer;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_ShouldReturnAccountResponseDTO() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        Account account = new Account();
        AccountResponseDTO expectedResponse = new AccountResponseDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountMapper.fromCustomerToAccount(eq(customer), anyString())).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.fromAccountToAccountDTO(account)).thenReturn(expectedResponse);

        // Act
        AccountResponseDTO actualResponse = accountService.createAccount(customerId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(customerRepository).findById(customerId);
        verify(accountMapper).fromCustomerToAccount(eq(customer), anyString());
        verify(accountRepository).save(account);
        verify(accountMapper).fromAccountToAccountDTO(account);
    }

    @Test
    void createAccount_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> accountService.createAccount(customerId));
        verify(customerRepository).findById(customerId);
    }

    @Test
    void getBalance_ShouldReturnBalanceResponseDTO() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000.0));
        BalanceResponseDTO expectedBalance = new BalanceResponseDTO(BigDecimal.valueOf(1000.0));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.fromBalanceToBalanceDTO(account.getBalance())).thenReturn(expectedBalance);

        // Act
        BalanceResponseDTO actualBalance = accountService.getBalance(accountId);

        // Assert
        assertEquals(expectedBalance, actualBalance);
        verify(accountRepository).findById(accountId);
        verify(accountMapper).fromBalanceToBalanceDTO(account.getBalance());
    }

    @Test
    void getBalance_ShouldThrowException_WhenAccountNotFound() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(accountId));
        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAccountFromId_ShouldReturnAccountResponseDTO() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        AccountResponseDTO expectedResponse = new AccountResponseDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.fromAccountToAccountResponseDTO(account)).thenReturn(expectedResponse);

        // Act
        AccountResponseDTO actualResponse = accountService.getAccountFromId(accountId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(accountRepository).findById(accountId);
        verify(accountMapper).fromAccountToAccountResponseDTO(account);
    }

    @Test
    void getAccountFromId_ShouldThrowException_WhenAccountNotFound() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountFromId(accountId));
        verify(accountRepository).findById(accountId);
    }

    @Test
    void deleteAccount_ShouldCallRepositoryDelete_WhenAccountExists() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(accountRepository.existsById(accountId)).thenReturn(true);

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository).existsById(accountId);
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void deleteAccount_ShouldThrowException_WhenAccountNotFound() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(accountRepository.existsById(accountId)).thenReturn(false);

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(accountId));
        verify(accountRepository).existsById(accountId);
    }
}
