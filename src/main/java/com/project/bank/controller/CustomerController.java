package com.project.bank.controller;

import com.project.bank.dto.CustomerIdRequestDTO;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.model.Customer;
import com.project.bank.service.CustomerService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerInfoDTO customerInfoDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerInfoDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping
    public ResponseEntity<CustomerInfoDTO> getCustomerDetails(@RequestBody CustomerIdRequestDTO customerIdRequestDTO){
        CustomerInfoDTO customerInfoDTO = customerService.getCustomerDetails(customerIdRequestDTO);
        return ResponseEntity.ok(customerInfoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> updateCustomer(@PathVariable("id") UUID id, @RequestBody CustomerInfoDTO customerInfoDTO){
        CustomerIdRequestDTO customerIdRequestDTO = new CustomerIdRequestDTO();
        customerIdRequestDTO.setId(id);
        CustomerInfoDTO updatedCustomer = customerService.updateCustomer(customerIdRequestDTO, customerInfoDTO);
        return ResponseEntity.ok(updatedCustomer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable @NotBlank String id ) {
        CustomerIdRequestDTO customerIdRequestDTO = new CustomerIdRequestDTO();
        customerIdRequestDTO.setId(UUID.fromString(id));
        customerService.deleteCustomer(customerIdRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
