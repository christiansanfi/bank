package com.project.bank.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetTransactionResponseDTO {
    private float amount;
    private String type;
    private LocalDateTime date;
}
