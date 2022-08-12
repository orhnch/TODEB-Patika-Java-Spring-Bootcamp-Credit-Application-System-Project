package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CustomerMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mvc;

    @Mock
    private CustomerService customerService;

    @Autowired
    private CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new GenericExceptionHandler()).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        //init step
        List<Customer> expectedCustomers = getSampleTestCustomers();

        //stub - when step
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        MockHttpServletResponse response = mvc.perform(get("/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        //then - validate step
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>() {
        });
        assertEquals(expectedCustomers.size(), actualCustomers.size());
    }

    @Test
    void createNewCustomer() throws Exception {
        //init step
        Customer expectedCustomer = getSampleTestCustomers().get(0);
        expectedCustomer.setId(null);
        ObjectMapper inputJson = new ObjectMapper();
        String inner = inputJson.writeValueAsString(expectedCustomer);

        //stub - when step
        when(customerService.create(Mockito.any())).thenReturn(expectedCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(inner)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();

        //then - validate step
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(inner);

    }

    @Test
    void getCustomerById() throws Exception {
        // init test values
        Customer expectedCustomer = getSampleTestCustomers().get(0);

        // stub - given
        when(customerService.getCustomerById(1L)).thenReturn(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(get("/customer/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getSalary(), actualCustomer.getSalary()),
                () -> assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber())
        );

    }


    @Test
    void getCustomerByNationalNumberId() throws Exception {
        // init test values
        Customer expectedCustomer = getSampleTestCustomers().get(0);

        // stub - given
        when(customerService.getCustomerByNationalNumberId("99999999990")).thenReturn(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(get("/customer/get/99999999990")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getSalary(), actualCustomer.getSalary()),
                () -> assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber())
        );
    }

    @Test
    void updateCustomer() throws Exception {
        // init test values
        CustomerDTO updateCustomerReqDTO = new CustomerDTO("99999999990", "Hamil", "Alak", 21, 5000, "05544127082");


        // stub - given
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestCustomerStr = objectWriter.writeValueAsString(updateCustomerReqDTO);


        MockHttpServletResponse response = mvc.perform(put("/customer/update/99999999990")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestCustomerStr))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }


    @Test
    void getCreditsByNationalNumberId() throws Exception {
        // init test values
        Customer expectedCustomer = getSampleTestCustomers().get(0);
        List<Credit> expectedCredits = getSampleTestCredits();
        expectedCustomer.setCredits(expectedCredits);

        // stub - given
        when(customerService.getCreditsByNationalNumberId(expectedCustomer.getNationalNumberId())).thenReturn(expectedCredits);
        MockHttpServletResponse response = mvc.perform(get("/customer//get/credits/99999999990")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Credit> actualCredits = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Credit>>() {
        });
        assertEquals(expectedCredits.size(), actualCredits.size());

    }

    @Test
    void deleteCustomer() throws Exception {
        // init test values
        willDoNothing().given(customerService).delete(1L);

        // stub - given
        ResultActions response = mvc.perform(delete("/customer/delete?id=1"));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    private List<Customer> getSampleTestCustomers() {
        List<Customer> sampleList = new ArrayList<>();
        Customer customer = new Customer(1L, "99999999990", "Hamit", "Ala", 19, 1, "05544127081", 1500, new ArrayList<>());
        Customer customer2 = new Customer(2L, "99999999992", "Ozan", "Banko", 25, 10000, "", 1514, new ArrayList<>());
        Customer customer3 = new Customer(3L, "99999999994", "Orhan", "Burgu", 28, 10200, "", 104, new ArrayList<>());
        sampleList.add(customer);
        sampleList.add(customer2);
        sampleList.add(customer3);
        return sampleList;
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