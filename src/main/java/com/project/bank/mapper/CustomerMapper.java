package com.project.bank.mapper;

import com.project.bank.dto.CustomerDTO;
import com.project.bank.dto.CustomerIdDTO;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerMapper {

    public CustomerDTO fromCustomerToCustomerDTo (Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setBirthDate(customer.getBirthDate());
        customerDTO.setBirthPlace(customer.getBirthPlace());
        customerDTO.setTaxCode(customer.getTaxCode());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;

    }

    public CustomerIdDTO fromCustomerIdToCustomerIdDTO (UUID id){
        CustomerIdDTO customerIdDTO = new CustomerIdDTO();
        customerIdDTO.setId(id);
        return customerIdDTO;
    }

    public UUID fromCustomerIdDtoToCustomerId (CustomerIdDTO customerIdDTO){
        return customerIdDTO.getId();
    }

    public CustomerInfoDTO fromCustomerInfoToCustomerInfoDto
    (String name, LocalDateTime birthDate, String birthPlace, String taxCode, String address){
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
        customerInfoDTO.setName(name);
        customerInfoDTO.setBirthDate(birthDate);
        customerInfoDTO.setBirthPlace(birthPlace);
        customerInfoDTO.setTaxCode(taxCode);
        customerInfoDTO.setAddress(address);
        return  customerInfoDTO;
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
