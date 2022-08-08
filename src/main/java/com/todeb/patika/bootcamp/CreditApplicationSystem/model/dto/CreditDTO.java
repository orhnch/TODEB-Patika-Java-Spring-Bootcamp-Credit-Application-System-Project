package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.enums.CreditStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDTO {
    private CreditStatus status;
    private int creditLimit;
}
