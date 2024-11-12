package com.project.bank.mapper;

import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerMapper {

    public CustomerResponseDTO fromCustomerToCustomerDTo(Customer customer) {
        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setBirthDate(customer.getBirthDate());
        customerDTO.setBirthPlace(customer.getBirthPlace());
        customerDTO.setTaxCode(customer.getTaxCode());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;

    }

    public UUID fromCustomerIdRequestDtoToCustomerId(CustomerIdRequestDTO customerIdDTO) {
        return customerIdDTO.getId();
    }

    public CustomerInfoDTO fromCustomerToCustomerInfoDto(Customer customer) {
        return CustomerInfoDTO.builder()
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .birthPlace(customer.getBirthPlace())
                .taxCode(customer.getTaxCode())
                .address(customer.getAddress())
                .build();
    }

    public Customer fromCustomerInfoDtoToCustomer(CustomerInfoDTO customerInfoDTO) {
        Customer customer = new Customer();
        customer.setName(customerInfoDTO.getName());
        customer.setBirthDate(customerInfoDTO.getBirthDate());
        customer.setBirthPlace(customerInfoDTO.getBirthPlace());
        customer.setTaxCode(customerInfoDTO.getTaxCode());
        customer.setAddress(customerInfoDTO.getAddress());
        return customer;
    }

    public void updateCustomerFromInfoDto(Customer customer, CustomerInfoDTO customerInfoDTO) {
        customer.setName(customerInfoDTO.getName());
        customer.setBirthDate(customerInfoDTO.getBirthDate());
        customer.setBirthPlace(customerInfoDTO.getBirthPlace());
        customer.setTaxCode(customerInfoDTO.getTaxCode());
        customer.setAddress(customerInfoDTO.getAddress());
    }

}
