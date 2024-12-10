package com.project.bank.controller;

import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create a new customer", description = "Add a new customer to the database")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerInfoDTO customerInfoDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerInfoDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @Operation(summary = "Get customer details", description = "Retrieve the details of a specific customer")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> getCustomerDetails(@PathVariable("id") UUID id) {
        CustomerInfoDTO customerInfoDTO = customerService.getCustomerDetails(id);
        return ResponseEntity.ok(customerInfoDTO);
    }

    @Operation(summary = "Update customer details", description = "Update the details of an existing customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> updateCustomer(@PathVariable("id") UUID id, @RequestBody CustomerInfoDTO customerInfoDTO) {
        CustomerInfoDTO updatedCustomer = customerService.updateCustomer(id, customerInfoDTO);
        return ResponseEntity.ok(updatedCustomer);
    }


    @Operation(summary = "Delete a customer", description = "Delete an existing customer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
