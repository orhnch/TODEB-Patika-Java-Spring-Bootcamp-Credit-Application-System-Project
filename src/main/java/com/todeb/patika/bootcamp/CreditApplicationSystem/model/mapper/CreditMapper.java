package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CreditDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;

public class CreditMapper {

    public static CreditDTO toDTO(Credit credit) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setStatus(credit.getStatus());
        return creditDTO;
    }

    public static Credit toEntity(CreditDTO creditDTO) {
        Credit credit = new Credit();
        credit.setStatus(creditDTO.getStatus());
        return credit;
    }

}
