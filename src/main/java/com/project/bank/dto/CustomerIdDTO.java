package com.project.bank.dto;

import java.util.UUID;

import com.project.bank.model.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerIdDTO {
    private UUID id;

    public CustomerIdDTO(Customer customer) {
        this.id = customer.getId();
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        return customer;
    }
}
