package com.project.bank.service;

import com.project.bank.model.Transaction;
import com.project.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getLastFiveTransactions(Long accountId) {
        return transactionRepository.findTop5ByAccountIdOrderByDateDesc(accountId);
    }
}

