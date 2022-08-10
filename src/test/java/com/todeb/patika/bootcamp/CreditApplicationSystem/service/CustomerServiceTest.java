package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CustomerMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;


    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers() {
        //init step
        List<Customer> expCustomerList = getSampleTestCustomers();

        //stub - when step
        when(customerRepository.findAll()).thenReturn(expCustomerList);

        //then - validate step
        List<Customer> actualCustomerList = customerService.getAllCustomers();

        assertEquals(expCustomerList.size(), actualCustomerList.size());

        expCustomerList = expCustomerList.stream().sorted(getCustomerComparator()).collect(Collectors.toList());
        actualCustomerList = actualCustomerList.stream().sorted(getCustomerComparator()).collect(Collectors.toList());
        for (int i = 0; i < expCustomerList.size(); i++) {
            Customer currExpectedCustomer = expCustomerList.get(i);
            Customer currActualCustomer = actualCustomerList.get(i);
            assertEquals(currExpectedCustomer.getId(), currActualCustomer.getId());
            assertEquals(currExpectedCustomer.getCredits(), currActualCustomer.getCredits());
            assertEquals(currExpectedCustomer.getCreditScore(), currActualCustomer.getCreditScore());
            assertEquals(currExpectedCustomer.getNationalNumberId(), currActualCustomer.getNationalNumberId());
            assertEquals(currExpectedCustomer.getPhoneNumber(), currActualCustomer.getPhoneNumber());
            assertEquals(currExpectedCustomer.getFirstName(), currActualCustomer.getFirstName());
            assertEquals(currExpectedCustomer.getLastName(), currActualCustomer.getLastName());
        }
    }

    @Test
    void create() {
        //init step
        Customer expectedCustomer = getSampleTestCustomers().get(0);
        expectedCustomer.setId(null);

        //stub - when step
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        //then - validate step
        Customer actualCustomer = customerService.create(expectedCustomer);

        verify(customerRepository, times((1))).save(expectedCustomer);

        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getSalary(), actualCustomer.getSalary()),
                () -> assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber())
        );

    }

    @Test
    void getCustomerById_successful() {
        //init step
        Customer expectedCustomer = getSampleTestCustomers().get(1);
        Optional<Customer> optExpectedCustomer = Optional.of(expectedCustomer);

        //stub - when step
        when(customerRepository.findById(anyLong())).thenReturn(optExpectedCustomer);

        //then - validate step
        Customer actualCustomer = customerService.getCustomerById(1L);

        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getSalary(), actualCustomer.getSalary()),
                () -> assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber())
        );
    }

    @Test
    void getCustomerById_NOT_FOUND() {
        //init step

        //stub - when step
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        //then - validate step
        assertThrows(EntityNotFoundException.class,
                () -> {
                    Customer actualCustomer = customerService.getCustomerById(1L);
                }
        );
    }


    @Test
    void getCustomerByNationalNumberId_successful() {
        //init step
        Customer expectedCustomer = getSampleTestCustomers().get(1);
        Optional<Customer> optExpectedCustomer = Optional.of(expectedCustomer);

        //stub - when step
        when(customerRepository.findCustomerByNationalNumberId(any())).thenReturn(optExpectedCustomer);

        //then - validate step
        Customer actualCustomer = customerService.getCustomerByNationalNumberId(any());

        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getSalary(), actualCustomer.getSalary()),
                () -> assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber())
        );
    }

    @Test
    void getCustomerByNationalNumberId_NOT_FOUND() {
        //init step

        //stub - when step
        when(customerRepository.findCustomerByNationalNumberId(any())).thenReturn(Optional.empty());

        //then - validate step
        assertThrows(EntityNotFoundException.class,
                () -> {
                    Customer actualCustomer = customerService.getCustomerByNationalNumberId("99999999990");
                }
        );
    }

    @Test
    void update() {
        //init step
        Customer expCustomer = getSampleTestCustomers().get(0);
        Optional<Customer> optExpCustomer = Optional.of(expCustomer);

        Customer updatedCustomer = new Customer(1L, "99999999990", "Yakup", "Cakmak", 50, 1, "05544127081", 1500, new ArrayList<>());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Yakup");
        customerDTO.setLastName("Cakmak");
        customerDTO.setAge(50);

        //stub - when step
        when(customerRepository.findCustomerByNationalNumberId(expCustomer.getNationalNumberId())).thenReturn(optExpCustomer);
        when(customerRepository.save(any())).thenReturn(updatedCustomer);


        Customer actualCustomer = customerService.update(expCustomer.getNationalNumberId(), customerDTO);


        //then - validate step

        assertEquals(expCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expCustomer.getAge(), actualCustomer.getAge());
    }

    @Test
    void getCreditsByNationalNumberId() {
        //init step
        Customer customer = getSampleTestCustomers().get(0);
        Optional<Customer> optExpectedCustomer = Optional.of(customer);
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED, null, null, customer);
        List<Credit> expList = new ArrayList<>();
        expList.add(credit);

        //stub - when step
        when(customerRepository.findCustomerByNationalNumberId(customer.getNationalNumberId())).thenReturn(optExpectedCustomer);
        Customer actualCustomer = optExpectedCustomer.get();
        List<Credit> expCredits = actualCustomer.getCredits();

        //then - validate step
        List<Credit> actualCredits = customerService.getCreditsByNationalNumberId(customer.getNationalNumberId());

        assertEquals(expCredits.size(), actualCredits.size());
        for (int i = 0; i < expCredits.size(); i++) {
            Credit currExpectedCredit = expCredits.get(i);
            Credit currActualCredit = actualCredits.get(i);
            assertEquals(currExpectedCredit.getId(), currActualCredit.getId());
            assertEquals(currExpectedCredit.getCreditLimit(), currActualCredit.getCreditLimit());
            assertEquals(currExpectedCredit.getStatus(), currActualCredit.getStatus());
            assertEquals(currExpectedCredit.getCustomer(), currActualCredit.getCustomer());
        }
    }

    @Test
    void delete() {
        //init step
        Customer customer = getSampleTestCustomers().get(0);


        //stub - when step
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        //then - validate step
        customerService.delete(customer.getId());
        verify(customerRepository).deleteById(customer.getId());
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


    private Comparator<Customer> getCustomerComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }
}