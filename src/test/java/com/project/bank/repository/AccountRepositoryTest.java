package com.project.bank.repository;

import com.project.bank.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void saveAccount_ShouldPersistAccount() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));

        entityManager.persist(account);
        entityManager.flush();

        Account foundAccount = entityManager.find(Account.class, account.getId());
        assertNotNull(foundAccount);
        assertEquals("IT60X0542811101000000123456", foundAccount.getIban());
    }

    @Test
    void updateAccount_ShouldChangeBalance() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));
        entityManager.persist(account);

        account.setBalance(BigDecimal.valueOf(1500.0));
        entityManager.merge(account);
        entityManager.flush();

        Account updatedAccount = entityManager.find(Account.class, account.getId());
        assertEquals(BigDecimal.valueOf(1500.0), updatedAccount.getBalance());
    }

    @Test
    void deleteAccount_ShouldRemoveAccount() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setIban("IT60X0542811101000000123456");
        entityManager.persist(account);

        entityManager.remove(account);
        entityManager.flush();

        Account deletedAccount = entityManager.find(Account.class, account.getId());
        assertNull(deletedAccount);
    }
}