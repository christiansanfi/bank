package com.project.bank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Transaction {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private float amount;
    private String type;
    private LocalDateTime date;
}