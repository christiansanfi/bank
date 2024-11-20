package com.project.bank.controller;

import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerInfoDTO customerInfoDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerInfoDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> getCustomerDetails(@PathVariable("id") UUID id) {
        CustomerInfoDTO customerInfoDTO = customerService.getCustomerDetails(id);
        return ResponseEntity.ok(customerInfoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> updateCustomer(@PathVariable("id") UUID id, @RequestBody CustomerInfoDTO customerInfoDTO) {
        CustomerInfoDTO updatedCustomer = customerService.updateCustomer(id, customerInfoDTO);
        return ResponseEntity.ok(updatedCustomer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
