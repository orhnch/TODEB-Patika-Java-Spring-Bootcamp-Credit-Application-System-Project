package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
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

    public Customer update(String nationalNumberId, CustomerDTO customer) {
        Customer updatedCustomer = getCustomerByNationalNumberId(nationalNumberId);
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

    public void doApplication(Long id) {
        Customer customerById = getCustomerById(id);
        if (customerById.getCredits().size() == 0) {
            Credit credit = new Credit();
            List<Credit> credits = new ArrayList<>();

            credit.setCreditLimit(calculateCreditLimit(customerById.getCreditScore(), customerById.getSalary()));
            credit.setStatus(applicationResult(customerById.getCreditScore()));
            credits.add(credit);
            customerById.setCredits(credits);
            credit.setCustomer(customerById);
            customerRepository.save(customerById);
        } else {
            throw new AlreadyExistException(customerById.getNationalNumberId()," has made a credit application before.");
        }

    }

    public String sendSMS(Long id){
        Customer customerById = getCustomerById(id);
        if (customerById.getCredits().size() == 1){
            return  "Credit application result as "
            +customerById.getCredits().get(0).getStatus().toString().toUpperCase()
            +" was sent to "
            +customerById.getPhoneNumber()
            +" by SMS! Credit Limit: "
            +customerById.getCredits().get(0).getCreditLimit();
        }else {
            return "There is no credit application! SMS could not sent!";
        }
    }

    public List<Credit> getCreditsByNationalNumberId(String nationalNumberId) {
        Customer customer = getCustomerByNationalNumberId(nationalNumberId);
        List<Credit> credits = new ArrayList<>(customer.getCredits());
        return credits;
    }

    //////////////////////CALCULATIONS//////////////////////////////////////////////
    private CreditStatus applicationResult(int creditScore) {
        if(creditScore<=0){
            throw new EntityNotFoundException("customer","credit score : 0");
        }

        if (creditScore >= CreditScoreLimit.LOWER.getValue()) {
            return CreditStatus.APPROVED;
        }
        return CreditStatus.REJECTED;
    }

    private Integer calculateCreditLimit(int creditScore, int salary) {
        if(creditScore<=0){
            throw new EntityNotFoundException("customer","credit score : 0");
        }

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
