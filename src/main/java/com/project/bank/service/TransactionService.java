package com.project.bank.service;

import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.GetTransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.exception.*;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.mapper.TransactionMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    public final TransactionRepository transactionRepository;
    public final TransactionMapper transactionMapper;
    public final AccountMapper accountMapper;
    public final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountMapper accountMapper, AccountRepository accountRepository){
        this.transactionRepository=transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    public GetTransactionResponseDTO deposit(TransactionRequestDTO transactionRequestDTO){
            if (isAmountPositive(transactionRequestDTO.getAmount())){
                UUID id = transactionRequestDTO.getAccountId();
                Account account = accountRepository.getReferenceById(id);
                Transaction transaction = transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO,"deposit",account);
                transaction = transactionRepository.save(transaction);
                updateBalance(account,transactionRequestDTO.getAmount());
                account = accountRepository.save(account);
                return transactionMapper.fromTransactionToGetTransactionResponseDto(transaction);
            } else {
                throw new NegativeAmountException("Amount must be higher than 0");
            }
    }

    public GetTransactionResponseDTO withdraw(TransactionRequestDTO transactionRequestDTO){
        if (isAmountPositive(transactionRequestDTO.getAmount())){
            UUID id = transactionRequestDTO.getAccountId();
            Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with id: " + id + " not found"));
            if (isBalanceSufficient(account.getBalance(),transactionRequestDTO.getAmount())){
                Transaction transaction = transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO,"withdraw",account);
                transaction = transactionRepository.save(transaction);
                updateBalance(account,transactionRequestDTO.getAmount().negate());
                account = accountRepository.save(account);
                return transactionMapper.fromTransactionToGetTransactionResponseDto(transaction);
            } throw new InsufficientBalanceException("Balance is not sufficient to cover the withdraw");
        } throw new NegativeAmountException("Amount must be higher than 0");
    }

    private void updateBalance(Account account, BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        balance = balance.add(amount);
        account.setBalance(balance);
    }

    private boolean isAmountPositive(BigDecimal amount){
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isBalanceSufficient(BigDecimal accountBalance, BigDecimal withdrawAmount){
        return accountBalance.compareTo(withdrawAmount) >= 0;
    }

    public List<GetTransactionResponseDTO> getLastFiveTransactions(UUID accountId) {
        Pageable pageable = PageRequest.of(0,5);
        List<Transaction> transactions = transactionRepository.findTop5ByAccountIdOrderByDateDesc(accountId, pageable);
        return transactions.stream()
                .map(transactionMapper::fromTransactionToGetTransactionResponseDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteTransaction (UUID id){
        if (transactionRepository.existsById(id)){
            transactionRepository.deleteById(id);
        } else {
            throw new TransactionNotFoundException("Transaction not found");
        }
    }
}

