package com.project.bank.repository;

import com.project.bank.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void saveCustomer_ShouldPersistCustomer() {
        Customer customer = new Customer();
        customer.setName("Mario Rossi");
        customer.setBirthPlace("Roma");
        customer.setTaxCode("MRARSS90E20H501X");

        customerRepository.save(customer);

        Customer persistedCustomer = entityManager.find(Customer.class, customer.getId());
        assertNotNull(persistedCustomer);
        assertEquals("Mario Rossi", persistedCustomer.getName());
        assertEquals("Roma", persistedCustomer.getBirthPlace());
        assertEquals("MRARSS90E20H501X", persistedCustomer.getTaxCode());
    }

    @Test
    void updateCustomer_ShouldUpdateCustomerDetails() {
        Customer customer = new Customer();
        customer.setName("Mario Rossi");
        customer.setBirthPlace("Roma");
        customer.setTaxCode("MRARSS90E20H501X");
        customerRepository.save(customer);

        customer.setName("Mario Verdi");
        customer.setBirthPlace("Milano");
        customerRepository.save(customer);

        Customer updatedCustomer = entityManager.find(Customer.class, customer.getId());
        assertNotNull(updatedCustomer);
        assertEquals("Mario Verdi", updatedCustomer.getName());
        assertEquals("Milano", updatedCustomer.getBirthPlace());
    }

    @Test
    void deleteCustomer_ShouldRemoveCustomer() {
        Customer customer = new Customer();
        customer.setName("Mario Rossi");
        customer.setBirthPlace("Roma");
        customer.setTaxCode("MRARSS90E20H501X");
        customerRepository.save(customer);

        customerRepository.delete(customer);

        Customer deletedCustomer = entityManager.find(Customer.class, customer.getId());
        assertNull(deletedCustomer);
    }
}
