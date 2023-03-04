package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@Data
@RequiredArgsConstructor
public class CreditPayloadDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private CreditStatus status;
    private int creditLimit;
}

