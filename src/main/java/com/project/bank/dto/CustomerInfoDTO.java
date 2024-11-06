package com.project.bank.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CustomerInfoDTO {
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private String taxCode;
    private String address;
}
