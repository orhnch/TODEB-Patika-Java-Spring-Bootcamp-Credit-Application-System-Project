package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {
    private final CustomerService customerService;

    public Credit doApplication(String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customerByNationalNumberId.getCredits().size() == 0) {
            Credit credit = new Credit();
            List<Credit> credits = new ArrayList<>();

            credit.setCreditLimit(calculateCreditLimit(customerByNationalNumberId.getCreditScore(), customerByNationalNumberId.getSalary()));
            credit.setStatus(applicationResult(customerByNationalNumberId.getCreditScore()));
            credits.add(credit);
            customerByNationalNumberId.setCredits(credits);
            credit.setCustomer(customerByNationalNumberId);
            customerService.save(customerByNationalNumberId);
            return credit;
        } else {
            throw new AlreadyExistException(customerByNationalNumberId.getNationalNumberId(), " has made a credit application before.");
        }

    }

    public String sendSMS(String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customerByNationalNumberId.getCredits().size() == 1) {
            return "Credit application result as "
                    + customerByNationalNumberId.getCredits().get(0).getStatus().toString().toUpperCase()
                    + " was sent to "
                    + customerByNationalNumberId.getPhoneNumber()
                    + " by SMS! Credit Limit: "
                    + customerByNationalNumberId.getCredits().get(0).getCreditLimit();
        } else {
            throw new EntityNotFoundException("customer credit application", "customer national number id: " + nationalNumberId);
        }
    }

    //////////////////////CALCULATIONS//////////////////////////////////////////////


    private CreditStatus applicationResult(int creditScore) {
        if (creditScore <= 0) {
            throw new EntityNotFoundException("customer", "credit score : 0");
        }

        if (creditScore >= CreditScoreLimit.LOWER.getValue()) {
            return CreditStatus.APPROVED;
        }
        return CreditStatus.REJECTED;
    }

    private Integer calculateCreditLimit(int creditScore, int salary) {
        if (creditScore <= 0) {
            throw new EntityNotFoundException("customer", "credit score : 0");
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
