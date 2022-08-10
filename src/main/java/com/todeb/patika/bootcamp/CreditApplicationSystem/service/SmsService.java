package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Sms;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.SmsStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsRepository smsRepository;
    private final CreditService creditService;
    private final CustomerService customerService;

    public String sendSMS(String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        if (customerByNationalNumberId.getCredits().size() == 1) {
            Credit credit = customerByNationalNumberId.getCredits().get(0);
            Sms sms = credit.getSms();
            sms.setSmsStatus(SmsStatus.SENT);
            smsRepository.save(sms);
            creditService.save(credit);
            customerService.save(customerByNationalNumberId);
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

    public Sms save(Sms sms) {
        Sms savedSms = smsRepository.save(sms);
        return savedSms;
    }
}
