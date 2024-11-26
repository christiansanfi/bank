package com.project.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO("Mario Rossi", null, "Roma", "MRARSS90E20H501X", "Via Roma 1");
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();

        when(customerService.createCustomer(customerInfoDTO)).thenReturn(customerResponseDTO);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerResponseDTO)));
    }

    @Test
    void getCustomerDetails_ShouldReturnCustomerInfoDTO() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO("Mario Rossi", null, "Roma", "MRARSS90E20H501X", "Via Roma 1");

        when(customerService.getCustomerDetails(customerId)).thenReturn(customerInfoDTO);

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerInfoDTO)));
    }

    @Test
    void getCustomerDetails_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        UUID customerId = UUID.randomUUID();

        when(customerService.getCustomerDetails(customerId)).thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO("Mario Rossi", null, "Roma", "MRARSS90E20H501X", "Via Roma 1");
        CustomerInfoDTO updatedCustomerInfoDTO = new CustomerInfoDTO("Mario Verdi", null, "Milano", "MRVRDI90E20H501X", "Via Milano 10");

        when(customerService.updateCustomer(customerId, customerInfoDTO)).thenReturn(updatedCustomerInfoDTO);

        mockMvc.perform(put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCustomerInfoDTO)));
    }

    @Test
    void updateCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO("Mario Rossi", null, "Roma", "MRARSS90E20H501X", "Via Roma 1");

        when(customerService.updateCustomer(customerId, customerInfoDTO)).thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerInfoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void deleteCustomer_ShouldReturnNoContent() throws Exception {
        UUID customerId = UUID.randomUUID();
        doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        UUID customerId = UUID.randomUUID();

        doThrow(new CustomerNotFoundException("Customer not found")).when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }
}
