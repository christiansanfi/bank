package com.project.bank.repository;

import com.project.bank.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void saveAccount_ShouldPersistAccount() {
        Account account = new Account();
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));

        accountRepository.save(account);

        Account persistedAccount = entityManager.find(Account.class, account.getId());
        assertNotNull(persistedAccount);
        assertNotNull(persistedAccount.getId());
        assertTrue(!persistedAccount.getId().toString().isBlank());

        assertEquals(persistedAccount.getIban(), account.getIban());
        assertEquals(persistedAccount.getBalance(), account.getBalance());
    }

    @Test
    void updateAccount_ShouldUpdateBalance() {
        Account account = new Account();
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));
        accountRepository.save(account);

        account.setBalance(BigDecimal.valueOf(1500.0));

        accountRepository.save(account);

        Account persistedAccount = entityManager.find(Account.class, account.getId());
        assertEquals(0, persistedAccount.getBalance().compareTo(account.getBalance()));
        assertNotNull(persistedAccount);
        assertEquals(account.getIban(), persistedAccount.getIban());

    }

    @Test
    void deleteAccount_ShouldRemoveAccount() {
        Account account = new Account();
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.valueOf(1000.0));
        accountRepository.save(account);

        accountRepository.delete(account);

        Account deletedAccount = entityManager.find(Account.class, account.getId());

        assertNull(deletedAccount);
    }

}
