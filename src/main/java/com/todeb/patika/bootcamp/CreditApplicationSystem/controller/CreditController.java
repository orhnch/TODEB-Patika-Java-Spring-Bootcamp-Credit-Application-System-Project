package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.CreditPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CreditMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {

    private final CreditService creditService;

    private final CreditMapper CREDIT_MAPPER = Mappers.getMapper(CreditMapper.class);

    @GetMapping("/all")
    public ResponseEntity getAllCredits() {
        List<Credit> allCredits = creditService.getAllCredits();
        return ResponseEntity.ok(allCredits);
    }

    @DeleteMapping
    public ResponseEntity deleteCredit(@RequestParam(name = "id") @Min(1) Long id) {
        creditService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit deleted successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateCredit(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody CreditPayloadDTO credit) {
        Credit update = creditService.update(id, credit);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity deleteAllCredits() {
        creditService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All credits were deleted successfully");
    }
}
