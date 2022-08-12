package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("/credit/score")
public class CreditScoreController {
    @Autowired
    private CreditScoreService creditScoreService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("calculate/{nationalNumberId}")
    public ResponseEntity calculateCreditScore(
            @PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId) {
        Customer customer = creditScoreService.calculateCreditScore(nationalNumberId);
        return ResponseEntity.ok("Credit Score: " + customer.getCreditScore());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("reset/{nationalNumberId}")
    public ResponseEntity resetCreditScore(
            @PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId) {
        creditScoreService.resetCreditScore(nationalNumberId);
        ;
        return ResponseEntity.ok("Credit Score reset successfully.");
    }
}
