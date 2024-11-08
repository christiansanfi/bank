package com.project.bank.repository;

import com.project.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId ORDER BY t.date DESC")
    List<Transaction> findTop5ByAccountIdOrderByDateDesc(@Param("accountId") UUID accountId, Pageable pageable);
}

