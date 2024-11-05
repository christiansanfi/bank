package com.project.bank.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountIdRequestDTO {
    private UUID id;
}