package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CreditDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public List<Credit> getAllCredits() {
        List<Credit> allCredits = creditRepository.findAll();
        return allCredits;
    }


    public Credit getCreditById(Long id) {
        Optional<Credit> byId = creditRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Credit", "id: " + id));
    }

    public void delete(Long id) {
        getCreditById(id);
        creditRepository.deleteById(id);
    }

    public Credit update(Long id, CreditDTO credit) {
        Credit creditById = getCreditById(id);
        creditById.setCreditLimit(credit.getCreditLimit());
        creditById.setStatus(credit.getStatus());

        return creditRepository.save(creditById);
    }

    public void deleteAll() {
        creditRepository.deleteAll();
    }
}
