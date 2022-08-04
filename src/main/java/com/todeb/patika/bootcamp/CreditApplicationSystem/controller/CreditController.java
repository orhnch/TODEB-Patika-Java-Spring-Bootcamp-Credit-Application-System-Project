package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CreditDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    private CreditService creditService;

    @GetMapping("/all")
    public ResponseEntity getAllCredits() {
        List<Credit> allCredits = creditService.getAllCredits();
        return ResponseEntity.ok(allCredits);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCreditById(@PathVariable Long id) {
        Credit creditById = creditService.getCreditById(id);
        return ResponseEntity.status(HttpStatus.OK).body(creditById);
    }

    @PostMapping("/create")
    public ResponseEntity createNewCredit(@RequestBody CreditDTO credit) {
        Credit respCredit = creditService.create(credit);
        if (respCredit == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Credit could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCredit);
    }

    @DeleteMapping
    public ResponseEntity deleteCredit(@RequestParam(name = "id") Long id) {
        creditService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit deleted successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateCredit(
            @PathVariable Long id,
            @RequestBody CreditDTO customer) {
        Credit update = creditService.update(id, customer);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity deleteAllCredits() {
        creditService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All credits were deleted successfully");
    }
}
