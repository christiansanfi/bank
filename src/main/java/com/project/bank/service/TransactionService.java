package com.project.bank.service;

import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.exception.*;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.mapper.TransactionMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    public final TransactionRepository transactionRepository;
    public final TransactionMapper transactionMapper;
    public final AccountMapper accountMapper;
    public final AccountRepository accountRepository;


    public TransactionResponseDTO deposit(TransactionRequestDTO transactionRequestDTO) {
        UUID id = transactionRequestDTO.getAccountId();
        Account account = accountRepository.getReferenceById(id);
        Transaction transaction = transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO, "deposit", account);
        transaction = transactionRepository.save(transaction);
        updateBalance(account, transactionRequestDTO.getAmount());
        account = accountRepository.save(account);
        return transactionMapper.fromTransactionToGetTransactionResponseDto(transaction);
    }

    public TransactionResponseDTO withdraw(TransactionRequestDTO transactionRequestDTO) {

        UUID id = transactionRequestDTO.getAccountId();
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + id + " not found"));

        if (!isBalanceSufficient(account.getBalance(), transactionRequestDTO.getAmount())) {
            throw new InsufficientBalanceException("Balance is not sufficient to cover the withdraw");
        }

        Transaction transaction = transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO, "withdraw", account);
        transaction = transactionRepository.save(transaction);
        updateBalance(account, transactionRequestDTO.getAmount().negate());
        account = accountRepository.save(account);
        return transactionMapper.fromTransactionToGetTransactionResponseDto(transaction);
    }


    public List<TransactionResponseDTO> getLastFiveTransactions(UUID accountId) {

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .limit(5)
                .map(transactionMapper::fromTransactionToGetTransactionResponseDto)
                .toList();
        /*
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(transactionMapper::fromTransactionToGetTransactionResponseDto)
                .collect(Collectors.toUnmodifiableList());
        */
    }

    public void deleteTransaction(UUID id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            throw new TransactionNotFoundException("Transaction not found");
        }
    }

    private void updateBalance(Account account, BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        balance = balance.add(amount);
        account.setBalance(balance);
    }

    private boolean isBalanceSufficient(BigDecimal accountBalance, BigDecimal withdrawAmount) {
        return accountBalance.compareTo(withdrawAmount) >= 0;
    }
}

