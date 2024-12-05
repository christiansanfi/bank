package com.project.bank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private String taxCode;
    private String address;
}