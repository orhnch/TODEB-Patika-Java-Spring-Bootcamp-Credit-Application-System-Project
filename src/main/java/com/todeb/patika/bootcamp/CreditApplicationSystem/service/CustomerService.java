package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.*;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CustomerMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> allCustormers = customerRepository.findAll();
        return allCustormers;
    }

    public Customer create(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Customer", "id: " + id));
    }

    public Customer getCustomerByNationalNumberId(String nationalNumberId) {
        Optional<Customer> byNationalNumberId = customerRepository.findCustomerByNationalNumberId(nationalNumberId);
        return byNationalNumberId.orElseThrow(() -> new EntityNotFoundException("Customer", "nationalNumberId: " + nationalNumberId));
    }

    public void delete(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    public Customer update(String phoneNumber, CustomerDTO customer) {
        Optional<Customer> customerByPhoneNumber = customerRepository.findCustomerByPhoneNumber(phoneNumber);
        if (!customerByPhoneNumber.isPresent())
            throw new EntityNotFoundException("Customer", "phone number :" + phoneNumber);
        Customer updatedCustomer = customerByPhoneNumber.get();
        updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        updatedCustomer.setFirstName(customer.getFirstName());
        updatedCustomer.setLastName(customer.getLastName());
        updatedCustomer.setAge(customer.getAge());
        updatedCustomer.setSalary(customer.getSalary());

        return customerRepository.save(updatedCustomer);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

    public Customer doApplication(Long id) {
        Customer customerById = getCustomerById(id);
        Credit credit = new Credit();
        List<Credit> credits = new ArrayList<>();

        credit.setCreditLimit(calculateCreditLimit(customerById.getCreditScore(), customerById.getSalary()));
        credit.setStatus(applicationResult(customerById.getCreditScore()));
        credits.add(credit);
        customerById.setCredits(credits);
        credit.setCustomer(customerById);
        return customerRepository.save(customerById);
    }

    public List<Credit> getCreditsByNationalNumberId (String nationalNumberId){
        Customer customer = getCustomerByNationalNumberId(nationalNumberId);
        List<Credit> credits = new ArrayList<>(customer.getCredits());
        return credits;
    }

//////////////////////CALCULATIONS//////////////////////////////////////////////
    private CreditStatus applicationResult(int creditScore) {
        if (creditScore >= CreditScoreLimit.LOWER.getValue()) {
            return CreditStatus.APPROVED;
        }
        return CreditStatus.REJECTED;
    }

    private Integer calculateCreditLimit(int creditScore, int salary) {
        if (creditScore >= CreditScoreLimit.LOWER.getValue() && creditScore < CreditScoreLimit.HIGHER.getValue() && salary <= SalaryLimit.SALARY_LIMIT.getValue()) {
            return CreditLimitStage.LOWER_STAGE.getValue();
        } else if (creditScore >= CreditScoreLimit.LOWER.getValue() && creditScore < CreditScoreLimit.HIGHER.getValue()) {
            return CreditLimitStage.HIGHER_STAGE.getValue();
        } else if (creditScore >= CreditScoreLimit.HIGHER.getValue()) {
            return salary * CreditLimitMultiplier.CREDIT_LIMIT_MULTIPLIER.getValue();
        }
        return 0;
    }
}
