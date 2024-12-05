package com.project.bank.repository;

import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));
        entityManager.persist(account);
        entityManager.flush();
    }

    @Test
    void saveTransaction_ShouldPersistTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("deposit");
        transaction.setDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        Transaction persistedTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertNotNull(persistedTransaction);
        assertEquals(BigDecimal.valueOf(100.0), persistedTransaction.getAmount());
        assertEquals("deposit", persistedTransaction.getType());
        assertEquals(account.getId(), persistedTransaction.getAccount().getId());
    }

    @Test
    void updateTransaction_ShouldUpdateTransactionDetails() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("deposit");
        transaction.setDate(LocalDateTime.now());
        entityManager.persist(transaction);
        entityManager.flush();

        transaction.setAmount(BigDecimal.valueOf(150.0));
        transaction.setType("withdraw");
        transactionRepository.save(transaction);

        Transaction updatedTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertNotNull(updatedTransaction);
        assertEquals(BigDecimal.valueOf(150.0), updatedTransaction.getAmount());
        assertEquals("withdraw", updatedTransaction.getType());
    }

    @Test
    void deleteTransaction_ShouldRemoveTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("deposit");
        transaction.setDate(LocalDateTime.now());
        entityManager.persist(transaction);
        entityManager.flush();

        transactionRepository.delete(transaction);

        Transaction deletedTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertNull(deletedTransaction);
    }
}
