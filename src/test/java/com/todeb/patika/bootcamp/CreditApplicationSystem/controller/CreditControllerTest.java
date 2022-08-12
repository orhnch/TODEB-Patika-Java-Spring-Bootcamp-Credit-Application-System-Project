package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreditControllerTest {

    private MockMvc mvc;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private CreditController creditController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(creditController).setControllerAdvice(new GenericExceptionHandler()).build();
    }

    @Test
    void getAllCredits() throws Exception {
        //init step
        List<Credit> expectedCredits = getSampleTestCredits();

        //stub - when step
        when(creditService.getAllCredits()).thenReturn(expectedCredits);

        MockHttpServletResponse response = mvc.perform(get("/credit/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        //then - validate step
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Credit> actualCredits = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Credit>>() {
        });
        assertEquals(expectedCredits.size(), actualCredits.size());
    }

    @Test
    void deleteCredit() throws Exception{
        // init test values
        willDoNothing().given(creditService).delete(1L);

        // stub - given
        ResultActions response = mvc.perform(delete("/credit?id=1"));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    private List<Credit> getSampleTestCredits() {
        List<Credit> sampleListCredit = new ArrayList<>();
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED, null, null, null);
        Credit credit2 = new Credit(2L, 20000, CreditStatus.APPROVED, null, null, null);
        Credit credit3 = new Credit(3L, 20000, CreditStatus.APPROVED, null, null, null);
        sampleListCredit.add(credit);
        sampleListCredit.add(credit2);
        sampleListCredit.add(credit3);
        return sampleListCredit;
    }
}