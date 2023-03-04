package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.CustomerPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        log.info("All customers are getting...");
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    public Customer create(Customer customer) {
        log.info("The customer is creating...");
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> {
            log.error("Customer could not found by id: " +id);
            return new EntityNotFoundException("Customer", "id: " + id);
        });
    }

    public Customer getCustomerByNationalNumberId(String nationalNumberId) {
        Optional<Customer> byNationalNumberId = customerRepository.findCustomerByNationalNumberId(nationalNumberId);
        return byNationalNumberId.orElseThrow(() -> {
            log.error("Customer could not found by nationalNumberId: "+nationalNumberId);
            return new EntityNotFoundException("Customer", "nationalNumberId: " + nationalNumberId);
        });
    }

    public void delete(Long id) {
        getCustomerById(id);
        log.info("Customer is deleting...");
        customerRepository.deleteById(id);
    }

    public Customer update(String nationalNumberId, CustomerPayloadDTO customer) {
        Customer updatedCustomer = getCustomerByNationalNumberId(nationalNumberId);
        log.info("Customer is updating...");
        updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        updatedCustomer.setFirstName(customer.getFirstName());
        updatedCustomer.setLastName(customer.getLastName());
        updatedCustomer.setAge(customer.getAge());
        updatedCustomer.setSalary(customer.getSalary());

        return customerRepository.save(updatedCustomer);
    }

    public Customer save(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    public void deleteAll() {
        log.info("All customers are deleting...");
        customerRepository.deleteAll();
    }


    public List<Credit> getCreditsByNationalNumberId(String nationalNumberId) {
        Customer customer = getCustomerByNationalNumberId(nationalNumberId);
        log.info("Customer's credits are getting...");
        List<Credit> credits = new ArrayList<>(customer.getCredits());
        return credits;
    }


}
