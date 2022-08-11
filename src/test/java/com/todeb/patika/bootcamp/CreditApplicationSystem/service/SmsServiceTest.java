package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.SmsStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.SmsRepository;
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
class SmsServiceTest {
    @Mock
    private  CreditService creditService;

    @Mock
    private  CustomerService customerService;

    @Mock
    private SmsRepository smsRepository;

    @InjectMocks
    private  SmsService smsService;


    @Test
    void sendSMS_successful() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED, null, null, null);
        List<Credit> credits = new ArrayList<>();
        credits.add(credit);
        customer.setCredits(credits);

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);
        Sms sms = new Sms();
        sms.setSmsStatus(SmsStatus.SENT);
        customer.getCredits().get(0).setSms(sms);
        Sms sms1 = customer.getCredits().get(0).getSms();
        sms.setSmsStatus(SmsStatus.WAIT_TO_SEND);
        smsRepository.save(sms);

        //then - validate step
        smsService.sendSMS(customer.getNationalNumberId());
        Sms sms2 = customer.getCredits().get(0).getSms();

        assertEquals(sms2,sms1);
    }

    @Test
    void sendSMS_NOT_FOUND() {
        //init step
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());

        //stub - when step
        when(customerService.getCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(customer);


        //then - validate step

        assertThrows(EntityNotFoundException.class,
                () -> {
                     smsService.sendSMS(customer.getNationalNumberId());
                }
        );
    }
}