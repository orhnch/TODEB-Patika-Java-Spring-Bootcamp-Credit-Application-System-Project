package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CreditScoreService creditScoreService;

    @Test
    void calculateCreditScore_successful() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCreditScore((int) (Math.random() * 1900));
        Integer creditScore1 = customer.getCreditScore();
        customer.setCreditScore(0);


        //then - validate step
        creditScoreService.calculateCreditScore(customer.getNationalNumberId());
        Integer creditScore2 = customer.getCreditScore();

        assertEquals(creditScore1 > 0, creditScore2 > 0);
    }

    @Test
    void calculateCreditScore_ALREADY_EXIST() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCreditScore((int) (Math.random() * 1900));


        //then - validate step
        assertThrows(AlreadyExistException.class,
                () -> {
                    Customer actualCustomer = creditScoreService.calculateCreditScore(customer.getNationalNumberId());
                }
        );

    }

    @Test
    void resetCreditScore() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCreditScore(0);
        Integer creditScore1 = customer.getCreditScore();

        customer.setCreditScore((int) (Math.random() * 1900));

        //then - validate step
        creditScoreService.resetCreditScore(customer.getNationalNumberId());
        Integer creditScore2 = customer.getCreditScore();

        assertEquals(creditScore1 == 0, creditScore2 == 0);

    }
}