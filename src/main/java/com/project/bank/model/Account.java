package com.project.bank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String iban;
}