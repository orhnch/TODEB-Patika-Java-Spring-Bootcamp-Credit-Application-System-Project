package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CreditScoreControllerTest {

    private MockMvc mvc;

    @Mock
    private CreditScoreService creditScoreService;

    @InjectMocks
    private CreditScoreController creditScoreController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(creditScoreController).setControllerAdvice(new GenericExceptionHandler()).build();
    }

    @Test
    void calculateCreditScore() throws Exception {
        // init test values
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 0, new ArrayList<>());

        // stub - given
        Mockito.when(creditScoreService.calculateCreditScore("99999999990")).thenReturn(customer);
        ResultActions response = mvc.perform(put("/credit/score/calculate/99999999990"));


        // then
        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void resetCreditScore() throws Exception{
        // init test values
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 1500, new ArrayList<>());

        // stub - given
        willDoNothing().given(creditScoreService).resetCreditScore("99999999990");
        ResultActions response = mvc.perform(put("/credit/score/reset/99999999990"));


        // then
        response.andExpect(status().isOk()).andDo(print());
    }
}