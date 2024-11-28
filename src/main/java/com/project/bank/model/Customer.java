package com.project.bank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="customer")
public class Customer {
    @Id
    private UUID id;
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private String taxCode;
    private String address;
}