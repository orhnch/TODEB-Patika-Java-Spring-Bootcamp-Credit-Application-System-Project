package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{nationalNumberId}")
    public ResponseEntity sendSMS(@PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId) {
        String string = smsService.sendSMS(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(string);

    }
}
