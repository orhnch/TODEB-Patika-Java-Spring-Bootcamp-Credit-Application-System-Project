package com.todeb.patika.bootcamp.CreditApplicationSystem.repository;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);
}
