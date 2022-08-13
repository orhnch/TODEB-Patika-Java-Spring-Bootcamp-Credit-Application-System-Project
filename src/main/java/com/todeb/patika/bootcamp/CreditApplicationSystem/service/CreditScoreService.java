package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CustomerService customerService;


    public Customer calculateCreditScore(String nationalNumberId) {
        Customer customer = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customer.getCreditScore() > 0) {
            log.error("Customer has a calculated credit score at past!!!");
            throw new AlreadyExistException(customer.getNationalNumberId(), " has credit score was calculated before.");
        }
        customer.setCreditScore((int) (Math.random() * 1900));
        log.info("Customer credit score was calculated and set!");
        log.debug("Customer credit score: "+customer.getCreditScore());
        return customerService.save(customer);
    }

    public void resetCreditScore(String nationalNumberId){
        Customer customer = customerService.getCustomerByNationalNumberId(nationalNumberId);
        log.info("Customer credit score is resetting...");
        customer.setCreditScore(0);
        log.debug("Customer credit score: "+customer.getCreditScore());
        customerService.save(customer);
    }
}
