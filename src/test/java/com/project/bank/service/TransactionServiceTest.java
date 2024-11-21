package com.project.bank.service;

import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.exception.AccountNotFoundException;
import com.project.bank.exception.InsufficientBalanceException;
import com.project.bank.exception.TransactionNotFoundException;
import com.project.bank.mapper.TransactionMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void deposit_ShouldReturnTransactionResponseDTO() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(100));
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));
        Transaction transaction = new Transaction();
        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();

        when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        when(transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO, "deposit", account)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction)).thenReturn(expectedResponse);

        // Act
        TransactionResponseDTO actualResponse = transactionService.deposit(transactionRequestDTO);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(accountRepository).getReferenceById(accountId);
        verify(transactionRepository).save(transaction);
        verify(accountRepository).save(account);
    }

    @Test
    void withdraw_ShouldReturnTransactionResponseDTO_WhenBalanceIsSufficient() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(100));
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));
        Transaction transaction = new Transaction();
        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO, "withdraw", account)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction)).thenReturn(expectedResponse);

        // Act
        TransactionResponseDTO actualResponse = transactionService.withdraw(transactionRequestDTO);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(accountRepository).findById(accountId);
        verify(transactionRepository).save(transaction);
        verify(accountRepository).save(account);
    }

    @Test
    void withdraw_ShouldThrowException_WhenBalanceIsInsufficient() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(1000));
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> transactionService.withdraw(transactionRequestDTO));
        verify(accountRepository).findById(accountId);
    }

    @Test
    void getLastFiveTransactions_ShouldReturnTransactionResponseDTOList() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        Transaction transaction1 = new Transaction();
        transaction1.setDate(LocalDateTime.of(2024, 5, 6, 10, 0));
        Transaction transaction2 = new Transaction();
        transaction2.setDate(LocalDateTime.of(2023, 11, 9, 15, 30));
        List<Transaction> transactions = List.of(transaction1, transaction2);
        TransactionResponseDTO responseDTO1 = new TransactionResponseDTO();
        TransactionResponseDTO responseDTO2 = new TransactionResponseDTO();

        when(transactionRepository.findByAccountId(accountId)).thenReturn(transactions);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction1)).thenReturn(responseDTO1);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction2)).thenReturn(responseDTO2);

        // Act
        List<TransactionResponseDTO> actualResponses = transactionService.getLastFiveTransactions(accountId);

        // Assert
        assertEquals(2, actualResponses.size());
        assertEquals(responseDTO1, actualResponses.get(0));
        assertEquals(responseDTO2, actualResponses.get(1));
        verify(transactionRepository).findByAccountId(accountId);
    }

    @Test
    void deleteTransaction_ShouldDelete_WhenTransactionExists() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.existsById(transactionId)).thenReturn(true);

        // Act
        transactionService.deleteTransaction(transactionId);

        // Assert
        verify(transactionRepository).deleteById(transactionId);
    }

    @Test
    void deleteTransaction_ShouldThrowException_WhenTransactionNotFound() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.existsById(transactionId)).thenReturn(false);

        // Act & Assert
        assertThrows(TransactionNotFoundException.class, () -> transactionService.deleteTransaction(transactionId));
        verify(transactionRepository).existsById(transactionId);
    }
}
