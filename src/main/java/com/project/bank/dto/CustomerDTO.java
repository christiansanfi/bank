package com.project.bank.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.bank.model.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomerDTO {
    private UUID id;
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private String taxCode;
    private String address;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.birthDate = customer.getBirthDate();
        this.birthPlace = customer.getBirthPlace();
        this.taxCode = customer.getTaxCode();
        this.address = customer.getAddress();
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setName(this.name);
        customer.setBirthDate(this.birthDate);
        customer.setBirthPlace(this.birthPlace);
        customer.setTaxCode(this.taxCode);
        customer.setAddress(this.address);
        return customer;
    }
}
