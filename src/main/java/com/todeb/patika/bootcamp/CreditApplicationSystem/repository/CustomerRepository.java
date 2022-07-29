package com.todeb.patika.bootcamp.CreditApplicationSystem.repository;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
