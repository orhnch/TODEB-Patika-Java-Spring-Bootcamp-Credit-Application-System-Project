package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit/score")
public class CreditScoreController {
    @Autowired
    private CreditScoreService creditScoreService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("calculate/{id}")
    public ResponseEntity calculateCreditScore(
            @PathVariable Long id) {
        Customer customer = creditScoreService.calculateCreditScore(id);
        return ResponseEntity.ok("Credit Score: " + customer.getCreditScore());
    }
}
