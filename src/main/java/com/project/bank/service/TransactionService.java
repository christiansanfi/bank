package com.project.bank.service;

import com.project.bank.model.Transaction;
import com.project.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getLastFiveTransactions(UUID accountId) {
        return transactionRepository.findTop5ByAccountIdOrderByDateDesc(accountId);
    }
}

