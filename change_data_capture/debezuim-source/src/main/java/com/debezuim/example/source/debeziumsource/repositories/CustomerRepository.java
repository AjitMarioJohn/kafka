package com.debezuim.example.source.debeziumsource.repositories;

import com.debezuim.example.source.debeziumsource.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}