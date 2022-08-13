package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CreditDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public List<Credit> getAllCredits() {
        log.info("All credits are getting...");
        List<Credit> allCredits = creditRepository.findAll();
        return allCredits;
    }


    public Credit getCreditById(Long id) {
        Optional<Credit> byId = creditRepository.findById(id);
        return byId.orElseThrow(() -> {
            log.error("Credit could not found by id: "+id);
            return new EntityNotFoundException("Credit", "id: " + id);
        });
    }

    public void delete(Long id) {
        getCreditById(id);
        log.info("The credit is deleting...");
        creditRepository.deleteById(id);
    }

    public Credit update(Long id, CreditDTO credit) {
        Credit creditById = getCreditById(id);
        creditById.setCreditLimit(credit.getCreditLimit());
        creditById.setStatus(credit.getStatus());

        return creditRepository.save(creditById);
    }
    public Credit save(Credit credit){
        Credit savedCredit = creditRepository.save(credit);
        return savedCredit;
    }

    public void deleteAll() {
        log.info("All credits are deleting...");
        creditRepository.deleteAll();
    }
}
