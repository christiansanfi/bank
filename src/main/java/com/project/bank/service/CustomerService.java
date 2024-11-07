package com.project.bank.service;

import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.mapper.CustomerMapper;
import com.project.bank.model.Customer;
import com.project.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponseDTO createCustomer (CustomerInfoDTO customerInfoDTO){
        Customer customer = customerMapper.fromCustomerInfoDtoToCustomer(customerInfoDTO);
        customer.setId(UUID.randomUUID());
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerDTo(savedCustomer);
    }

    public CustomerInfoDTO getCustomerDetails(UUID id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID: " + id + " not found"));
        return customerMapper.fromCustomerToCustomerInfoDto(customer);
    }

    public CustomerInfoDTO updateCustomer(CustomerIdRequestDTO customerIdRequestDTO, CustomerInfoDTO customerInfoDTO){
        UUID id = customerMapper.fromCustomerIdRequestDtoToCustomerId(customerIdRequestDTO);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID: " + id + " not found"));
        customerMapper.updateCustomerFromInfoDto(customer, customerInfoDTO);
        customer = customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerInfoDto(customer);
    }

    public void deleteCustomer (CustomerIdRequestDTO customerIdRequestDTO){
        UUID id = customerMapper.fromCustomerIdRequestDtoToCustomerId(customerIdRequestDTO);
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer not found");
        }
    }



}
