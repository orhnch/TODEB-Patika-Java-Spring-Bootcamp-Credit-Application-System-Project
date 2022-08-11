package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditLimitMultiplier;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.SmsStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceTest {
    @Mock
    private CustomerService customerService;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private CreditApplicationService creditApplicationService;

    @Test
    void doApplication_WhenCreditScoreSmallerThan500() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 10000, "05544127081", 485, new ArrayList<>());
        Credit credit = new Credit(1L, 0, CreditStatus.REJECTED,null, new Sms(1L, SmsStatus.WAIT_TO_SEND), customer);
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCredits(credits);
        Integer expectedCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus expectedCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus expectedSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();
        customer.setCredits(new ArrayList<>());
        customerService.save(customer);


        //then - validate step
        creditApplicationService.doApplication(customer.getNationalNumberId());
        Integer actualCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus actualCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus actualSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();


        assertEquals(actualCreditLimit,expectedCreditLimit);
        assertEquals(actualCreditStatus,expectedCreditStatus);
        assertEquals(actualSmsStatus,expectedSmsStatus);

    }

    @Test
    void doApplication_WhenCreditScoreLargerThan500SmallerThan1000AndSalarySmallerThan5000() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 2500, "05544127081", 550, new ArrayList<>());
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED,null, new Sms(1L, SmsStatus.WAIT_TO_SEND), customer);
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCredits(credits);
        Integer expectedCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus expectedCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus expectedSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();
        customer.setCredits(new ArrayList<>());
        customerService.save(customer);


        //then - validate step
        creditApplicationService.doApplication(customer.getNationalNumberId());
        Integer actualCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus actualCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus actualSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();


        assertEquals(actualCreditLimit,expectedCreditLimit);
        assertEquals(actualCreditStatus,expectedCreditStatus);
        assertEquals(actualSmsStatus,expectedSmsStatus);

    }

    @Test
    void doApplication_WhenCreditScoreLargerThan500SmallerThan1000AndSalaryLargerThan5000() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 5500, "05544127081", 550, new ArrayList<>());
        Credit credit = new Credit(1L, 20000, CreditStatus.APPROVED,null, new Sms(1L, SmsStatus.WAIT_TO_SEND), customer);
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCredits(credits);
        Integer expectedCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus expectedCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus expectedSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();
        customer.setCredits(new ArrayList<>());
        customerService.save(customer);


        //then - validate step
        creditApplicationService.doApplication(customer.getNationalNumberId());
        Integer actualCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus actualCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus actualSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();


        assertEquals(actualCreditLimit,expectedCreditLimit);
        assertEquals(actualCreditStatus,expectedCreditStatus);
        assertEquals(actualSmsStatus,expectedSmsStatus);

    }

    @Test
    void doApplication_WhenCreditScoreEqualsOrLargerThan1000() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 6580, "05544127081", 1205, new ArrayList<>());
        Credit credit = new Credit(1L,0, CreditStatus.APPROVED,null, new Sms(1L, SmsStatus.WAIT_TO_SEND), customer);
        credit.setCreditLimit(CreditLimitMultiplier.CREDIT_LIMIT_MULTIPLIER.getValue()*customer.getSalary());
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCredits(credits);
        Integer expectedCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus expectedCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus expectedSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();
        customer.setCredits(new ArrayList<>());
        customerService.save(customer);


        //then - validate step
        creditApplicationService.doApplication(customer.getNationalNumberId());
        Integer actualCreditLimit = customer.getCredits().get(0).getCreditLimit();
        CreditStatus actualCreditStatus = customer.getCredits().get(0).getStatus();
        SmsStatus actualSmsStatus = customer.getCredits().get(0).getSms().getSmsStatus();


        assertEquals(actualCreditLimit,expectedCreditLimit);
        assertEquals(actualCreditStatus,expectedCreditStatus);
        assertEquals(actualSmsStatus,expectedSmsStatus);

    }

    @Test
    void doApplication_ALREADY_EXISTS() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 6580, "05544127081", 1205, new ArrayList<>());
        Credit credit = new Credit(1L,0, CreditStatus.APPROVED,null, new Sms(1L, SmsStatus.WAIT_TO_SEND), customer);
        credit.setCreditLimit(CreditLimitMultiplier.CREDIT_LIMIT_MULTIPLIER.getValue()*customer.getSalary());
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        customer.setCredits(credits);
        customerService.save(customer);


        //then - validate step

        assertThrows(AlreadyExistException.class,
                () -> {
                    Credit actualCredit = creditApplicationService.doApplication(customer.getNationalNumberId());
                }
        );

    }
}