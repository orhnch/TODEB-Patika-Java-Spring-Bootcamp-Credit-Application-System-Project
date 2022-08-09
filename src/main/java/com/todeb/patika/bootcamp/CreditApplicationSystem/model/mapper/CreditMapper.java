package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CreditDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditDTO toDTO(Credit credit);

    Credit toEntity(CreditDTO creditDTO);

}
