package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreditApplicationControllerTest {
    private MockMvc mvc;

    @Mock
    private CreditApplicationService creditApplicationService;

    @InjectMocks
    private CreditApplicationController creditApplicationController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(creditApplicationController).setControllerAdvice(new GenericExceptionHandler()).build();
    }


    @Test
    void doApplication() throws Exception {
        // init test values
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());
        List<Credit> sampleListCredit = new ArrayList<>();
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED, null, new Sms(), customer);
        sampleListCredit.add(credit);
        customer.setCredits(sampleListCredit);

        // stub - given
        Mockito.when(creditApplicationService.doApplication("99999999990")).thenReturn(credit);
        ResultActions response = mvc.perform(post("/application/99999999990"));


        // then
        response.andExpect(status().isOk()).andDo(print());
        Mockito.verify(creditApplicationService, Mockito.times(1)).doApplication(any());

    }

}