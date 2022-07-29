package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CustomerMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Customer create(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Customer", "id: " + id));
    }

    public void delete(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    public Customer update(String phoneNumber, CustomerDTO customer) {
        Optional<Customer> customerByPhoneNumber = customerRepository.findCustomerByPhoneNumber(phoneNumber);
        if (!customerByPhoneNumber.isPresent())
            throw new EntityNotFoundException("Customer", "phone number :" + phoneNumber);
        Customer updatedCustomer = customerByPhoneNumber.get();
        updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        updatedCustomer.setFirstName(customer.getFirstName());
        updatedCustomer.setLastName(customer.getLastName());
        updatedCustomer.setAge(customer.getAge());
        updatedCustomer.setSalary(customer.getSalary());

        return customerRepository.save(updatedCustomer);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
