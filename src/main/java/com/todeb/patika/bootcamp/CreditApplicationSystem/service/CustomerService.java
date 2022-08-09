package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> allCustormers = customerRepository.findAll();
        return allCustormers;
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Customer", "id: " + id));
    }

    public Customer getCustomerByNationalNumberId(String nationalNumberId) {
        Optional<Customer> byNationalNumberId = customerRepository.findCustomerByNationalNumberId(nationalNumberId);
        return byNationalNumberId.orElseThrow(() -> new EntityNotFoundException("Customer", "nationalNumberId: " + nationalNumberId));
    }

    public void delete(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    public Customer update(String nationalNumberId, CustomerDTO customer) {
        Customer updatedCustomer = getCustomerByNationalNumberId(nationalNumberId);
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
        customerRepository.deleteAll();
    }


    public List<Credit> getCreditsByNationalNumberId(String nationalNumberId) {
        Customer customer = getCustomerByNationalNumberId(nationalNumberId);
        List<Credit> credits = new ArrayList<>(customer.getCredits());
        return credits;
    }


}
