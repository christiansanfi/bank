package com.project.bank.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerIdRequestDTO {
    private UUID id;
}
