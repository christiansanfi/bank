package com.project.bank.service;

import com.project.bank.dto.CustomerInfoDTO;
import com.project.bank.dto.CustomerResponseDTO;
import com.project.bank.exception.CustomerNotFoundException;
import com.project.bank.mapper.CustomerMapper;
import com.project.bank.model.Customer;
import com.project.bank.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_ShouldReturnCustomerResponseDTO() {
        // Arrange
        CustomerInfoDTO customerInfoDTO = CustomerInfoDTO.builder()
                .name("Mario Rossi")
                .birthDate(null) // Inserire data reale, se disponibile
                .birthPlace("Roma")
                .taxCode("MRARSS90E20H501X")
                .address("Via Roma 1")
                .build();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        Customer savedCustomer = new Customer();
        CustomerResponseDTO expectedResponse = new CustomerResponseDTO();

        when(customerMapper.fromCustomerInfoDtoToCustomer(customerInfoDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.fromCustomerToCustomerDTo(savedCustomer)).thenReturn(expectedResponse);

        // Act
        CustomerResponseDTO actualResponse = customerService.createCustomer(customerInfoDTO);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(customerMapper).fromCustomerInfoDtoToCustomer(customerInfoDTO);
        verify(customerRepository).save(customer);
        verify(customerMapper).fromCustomerToCustomerDTo(savedCustomer);
    }

    @Test
    void getCustomerDetails_ShouldReturnCustomerInfoDTO() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        CustomerInfoDTO expectedInfoDTO = new CustomerInfoDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.fromCustomerToCustomerInfoDto(customer)).thenReturn(expectedInfoDTO);

        // Act
        CustomerInfoDTO actualInfoDTO = customerService.getCustomerDetails(customerId);

        // Assert
        assertEquals(expectedInfoDTO, actualInfoDTO);
        verify(customerRepository).findById(customerId);
        verify(customerMapper).fromCustomerToCustomerInfoDto(customer);
    }

    @Test
    void getCustomerDetails_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerDetails(customerId));
        verify(customerRepository).findById(customerId);
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomerInfoDTO() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
        Customer customer = new Customer();
        CustomerInfoDTO updatedInfoDTO = new CustomerInfoDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        doNothing().when(customerMapper).updateCustomerFromInfoDto(customer, customerInfoDTO);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.fromCustomerToCustomerInfoDto(customer)).thenReturn(updatedInfoDTO);

        // Act
        CustomerInfoDTO actualUpdatedInfoDTO = customerService.updateCustomer(customerId, customerInfoDTO);

        // Assert
        assertEquals(updatedInfoDTO, actualUpdatedInfoDTO);
        verify(customerRepository).findById(customerId);
        verify(customerMapper).updateCustomerFromInfoDto(customer, customerInfoDTO);
        verify(customerRepository).save(customer);
        verify(customerMapper).fromCustomerToCustomerInfoDto(customer);
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customerId, customerInfoDTO));
        verify(customerRepository).findById(customerId);
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository).existsById(customerId);
    }
}
