package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    public Customer calculateCreditScore(Long id){
        Customer customer = customerService.getCustomerById(id);
        if(customer.getCreditScore()>0) {
           throw new AlreadyExistException(customer.getNationalNumberId()," has credit score was calculated before.");
        }
        customer.setCreditScore((int) (Math.random() * 1500));
        return customerRepository.save(customer);
    }
}
