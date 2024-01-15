package com.debezuim.example.source.debeziumsource.repositories;

import com.debezuim.example.source.debeziumsource.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSaveCustomer() {
        Customer mockCustomer = createMockCustomer();
        customerRepository.save(mockCustomer);

        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.get(0);

        assertEquals(1, customers.size());
    }

    private Customer createMockCustomer() {
        return Customer.builder()
                .region("Quebec")
                .country("Pakistan")
                .name("Ignatius Vasquez")
                .phone("1-462-343-7413")
                .email("taciti.sociosqu.ad@hotmail.ca")
                .address("Ap #826-8657 Vivamus St.")
                .postal("59214")
                .id(1L)
                .build();
    }
}