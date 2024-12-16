package com.project.bank.service;

import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
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
    void makeTransaction_ShouldReturnTransactionResponseDTO_WhenDeposit() {
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(100), Transaction.Type.DEPOSIT);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));
        Transaction transaction = new Transaction();
        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(requestDTO, account)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction)).thenReturn(expectedResponse);

        TransactionResponseDTO actualResponse = transactionService.makeTransaction(requestDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(transactionRepository).save(transaction);
        verify(accountRepository).save(account);
    }

    @Test
    void makeTransaction_ShouldReturnTransactionResponseDTO_WhenWithdraw() {
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(100), Transaction.Type.WITHDRAW);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));
        Transaction transaction = new Transaction();
        TransactionResponseDTO expectedResponse = new TransactionResponseDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(requestDTO, account)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction)).thenReturn(expectedResponse);

        TransactionResponseDTO actualResponse = transactionService.makeTransaction(requestDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(transactionRepository).save(transaction);
        verify(accountRepository).save(account);
    }

    @Test
    void makeTransaction_ShouldThrowException_WhenBalanceIsInsufficient() {
        UUID accountId = UUID.randomUUID();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(accountId, BigDecimal.valueOf(1000), Transaction.Type.WITHDRAW);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.makeTransaction(requestDTO));
    }

    @Test
    void getLastFiveTransactions_ShouldReturnTransactionResponseDTOList() {
        UUID accountId = UUID.randomUUID();
        Transaction transaction1 = new Transaction();
        transaction1.setDate(java.time.LocalDateTime.now());
        Transaction transaction2 = new Transaction();
        transaction2.setDate(java.time.LocalDateTime.now());
        List<Transaction> transactions = List.of(transaction1, transaction2);
        TransactionResponseDTO responseDTO1 = new TransactionResponseDTO();
        TransactionResponseDTO responseDTO2 = new TransactionResponseDTO();

        when(transactionRepository.findByAccountId(accountId)).thenReturn(transactions);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction1)).thenReturn(responseDTO1);
        when(transactionMapper.fromTransactionToGetTransactionResponseDto(transaction2)).thenReturn(responseDTO2);

        List<TransactionResponseDTO> actualResponses = transactionService.getLastFiveTransactions(accountId);

        assertEquals(2, actualResponses.size());
        verify(transactionRepository).findByAccountId(accountId);
    }

    @Test
    void deleteTransaction_ShouldDelete_WhenTransactionExists() {
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.existsById(transactionId)).thenReturn(true);

        transactionService.deleteTransaction(transactionId);

        verify(transactionRepository).deleteById(transactionId);
    }

    @Test
    void deleteTransaction_ShouldThrowException_WhenTransactionNotFound() {
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.existsById(transactionId)).thenReturn(false);

        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.deleteTransaction(transactionId));
    }
}
