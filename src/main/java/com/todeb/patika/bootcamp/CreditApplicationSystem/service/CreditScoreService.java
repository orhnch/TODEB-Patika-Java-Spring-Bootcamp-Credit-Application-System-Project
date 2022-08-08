package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CustomerService customerService;


    public Customer calculateCreditScore(String nationalNumberId) {
        Customer customer = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customer.getCreditScore() > 0) {
            throw new AlreadyExistException(customer.getNationalNumberId(), " has credit score was calculated before.");
        }
        customer.setCreditScore((int) (Math.random() * 1900));
        return customerService.save(customer);
    }
}
