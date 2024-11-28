package com.project.bank.repository;

import com.project.bank.model.Account;
import com.project.bank.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void saveTransaction_ShouldPersistTransaction() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setIban("IT60X0542811101000000123456");
        entityManager.persist(account);

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("deposit");
        transaction.setDate(LocalDateTime.now());

        entityManager.persist(transaction);
        entityManager.flush();

        Transaction foundTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertNotNull(foundTransaction);
        assertEquals(BigDecimal.valueOf(100.0), foundTransaction.getAmount());
    }

    @Test
    void findTransactionsByAccountId_ShouldReturnTransactions() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setIban("IT60X0542811101000000123456");
        entityManager.persist(account);

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setAccount(account);
        transaction1.setAmount(BigDecimal.valueOf(100.0));
        transaction1.setType("deposit");
        transaction1.setDate(LocalDateTime.now().minusDays(1));
        entityManager.persist(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID());
        transaction2.setAccount(account);
        transaction2.setAmount(BigDecimal.valueOf(50.0));
        transaction2.setType("withdraw");
        transaction2.setDate(LocalDateTime.now());
        entityManager.persist(transaction2);

        entityManager.flush();

        List<Transaction> transactions = entityManager
                .createQuery("SELECT t FROM Transaction t WHERE t.account.id = :accountId", Transaction.class)
                .setParameter("accountId", account.getId())
                .getResultList();

        assertEquals(2, transactions.size());
    }

    @Test
    void deleteTransaction_ShouldRemoveTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("deposit");
        transaction.setDate(LocalDateTime.now());
        entityManager.persist(transaction);

        entityManager.remove(transaction);
        entityManager.flush();

        Transaction deletedTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertNull(deletedTransaction);
    }
}