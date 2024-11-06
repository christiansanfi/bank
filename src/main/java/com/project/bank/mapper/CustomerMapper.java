package com.project.bank.mapper;

import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerMapper {

    public CustomerResponseDTO fromCustomerToCustomerDTo (Customer customer){
        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setBirthDate(customer.getBirthDate());
        customerDTO.setBirthPlace(customer.getBirthPlace());
        customerDTO.setTaxCode(customer.getTaxCode());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;

    }

    public CustomerIdRequestDTO fromCustomerIdToCustomerIdDTO (UUID id){
        CustomerIdRequestDTO customerIdDTO = new CustomerIdRequestDTO();
        customerIdDTO.setId(id);
        return customerIdDTO;
    }

    public UUID fromCustomerIdRequestDtoToCustomerId (CustomerIdRequestDTO customerIdDTO){
        return customerIdDTO.getId();
    }

    public CustomerInfoDTO fromCustomerToCustomerInfoDto (Customer customer){
        return  CustomerInfoDTO.builder()
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .birthPlace(customer.getBirthPlace())
                .taxCode(customer.getTaxCode())
                .address(customer.getAddress())
                .build();
    }

    public CustomerInfoDTO fromCustomerToCustomerInfoDto (Optional<Customer> customer){
        return CustomerInfoDTO.builder()
                .name(customer.get().getName())
                .birthDate(customer.get().getBirthDate())
                .birthPlace(customer.get().getBirthPlace())
                .taxCode(customer.get().getTaxCode())
                .address(customer.get().getAddress())
                .build();
    }

    public Customer fromCustomerInfoDtoToCustomer (CustomerInfoDTO customerInfoDTO){
        Customer customer = new Customer();
        customer.setName(customerInfoDTO.getName());
        customer.setBirthDate(customerInfoDTO.getBirthDate());
        customer.setBirthPlace(customerInfoDTO.getBirthPlace());
        customer.setTaxCode(customerInfoDTO.getTaxCode());
        customer.setAddress(customerInfoDTO.getAddress());
        return customer;
    }
}
