package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.AlreadyExistException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditApplicationService {
    private final CustomerService customerService;
    private final SmsService smsService;

    public Credit doApplication(String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customerByNationalNumberId.getCredits().size() == 0) {
            log.info("Credit application is making...");
            Credit credit = new Credit();
            List<Credit> credits = new ArrayList<>();
            Sms sms = new Sms();
            sms.setSmsStatus(SmsStatus.WAIT_TO_SEND);
            smsService.save(sms);

            credit.setCreditLimit(calculateCreditLimit(customerByNationalNumberId.getCreditScore(), customerByNationalNumberId.getSalary()));
            credit.setStatus(applicationResult(customerByNationalNumberId.getCreditScore()));
            credit.setSms(sms);
            credits.add(credit);
            customerByNationalNumberId.setCredits(credits);
            credit.setCustomer(customerByNationalNumberId);
            customerService.save(customerByNationalNumberId);
            return credit;
        } else {
            log.error("Customer has made a credit application before!!! Please check this.");
            throw new AlreadyExistException(customerByNationalNumberId.getNationalNumberId(), " has made a credit application before.");
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
