package com.project.bank.service;

import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.GetTransactionResponseDTO;
import com.project.bank.dto.TransactionRequestDTO;
import com.project.bank.dto.TransactionResponseDTO;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.exception.InsufficientBalanceException;
import com.project.bank.exception.NegativeAmountException;
import com.project.bank.exception.TransactionNotFoundException;
import com.project.bank.mapper.AccountMapper;
import com.project.bank.mapper.TransactionMapper;
import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
            if (transactionRequestDTO.getAmount().compareTo(BigDecimal.ZERO)>0){
                UUID id = transactionRequestDTO.getAccountId();
                Account account = accountRepository.getReferenceById(id);

                Transaction transaction = transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO,"deposit",account);
                transaction = transactionRepository.save(transaction);
                BigDecimal balance = account.getBalance();
                balance = balance.add(transactionRequestDTO.getAmount());
                account.setBalance(balance);
                account = accountRepository.save(account);
                return transactionMapper.fromTransactionToGetTransactionResponseDto(transaction);
            } else {
                throw new NegativeAmountException("Amount must be higher than 0");
            }
    }
/*
    public TransactionResponseDTO withdraw(TransactionRequestDTO transactionRequestDTO){
        UUID id = transactionRequestDTO.getAccountId();
        Account account = accountRepository.getReferenceById(id);
        BigDecimal balance = account.getBalance();
        if (transactionRequestDTO.getAmount().compareTo(BigDecimal.ZERO) > 0){
            if (balance.compareTo(transactionRequestDTO.getAmount()) >= 0){
                return transactionMapper.fromTransactionRequestDTOToTransactionResponseDTO(transactionRequestDTO, "withdraw");
            } throw new InsufficientBalanceException("Balance is not sufficient to cover the withdraw");
        }throw new NegativeAmountException("Amount must be higher than 0");
    }*/

    /*
    public List<Transaction> getLastFiveTransactions(UUID accountId) {
        return transactionRepository.findTop5ByAccountIdOrderByDateDesc(accountId);
    }
    */

    public void deleteTransaction (UUID id){
        if (transactionRepository.existsById(id)){
            transactionRepository.deleteById(id);
        } else {
            throw new TransactionNotFoundException("Transaction not found");
        }
    }
}

