package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private CreditStatus status;
    private int creditLimit;
}
