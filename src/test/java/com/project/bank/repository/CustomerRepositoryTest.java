package com.project.bank.repository;

import com.project.bank.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void saveCustomer_ShouldPersistCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Mario Rossi");
        customer.setTaxCode("MRARSS90E20H501X");

        entityManager.persist(customer);
        entityManager.flush();

        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
        assertNotNull(foundCustomer);
        assertEquals("Mario Rossi", foundCustomer.getName());
    }

    @Test
    void updateCustomer_ShouldChangeAddress() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Mario Rossi");
        customer.setAddress("Via Roma 1");
        entityManager.persist(customer);

        customer.setAddress("Via Milano 2");
        entityManager.merge(customer);
        entityManager.flush();

        Customer updatedCustomer = entityManager.find(Customer.class, customer.getId());
        assertEquals("Via Milano 2", updatedCustomer.getAddress());
    }

    @Test
    void deleteCustomer_ShouldRemoveCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Mario Rossi");
        entityManager.persist(customer);

        entityManager.remove(customer);
        entityManager.flush();

        Customer deletedCustomer = entityManager.find(Customer.class, customer.getId());
        assertNull(deletedCustomer);
    }
}