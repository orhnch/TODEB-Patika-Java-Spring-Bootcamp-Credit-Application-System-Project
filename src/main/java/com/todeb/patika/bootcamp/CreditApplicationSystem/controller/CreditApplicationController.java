package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class CreditApplicationController {

    @Autowired
    private CreditApplicationService creditApplicationService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{nationalNumberId}")
    public ResponseEntity doApplication(@PathVariable String nationalNumberId) {
        creditApplicationService.doApplication(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body("Credit application was made successfully!");

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("sms/{nationalNumberId}")
    public ResponseEntity sendSMS(@PathVariable String nationalNumberId) {
        String string = creditApplicationService.sendSMS(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(string);

    }
}