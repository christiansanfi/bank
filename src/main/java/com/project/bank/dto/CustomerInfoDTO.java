package com.project.bank.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoDTO {
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private String taxCode;
    private String address;
}
