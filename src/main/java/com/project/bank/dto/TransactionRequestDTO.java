package com.project.bank.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransactionRequestDTO {
    private UUID accountId;
    private float amount;
}
