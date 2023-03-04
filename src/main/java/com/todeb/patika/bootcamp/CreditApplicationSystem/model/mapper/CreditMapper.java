package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.CreditPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditPayloadDTO toDTO(Credit credit);

    Credit toEntity(CreditPayloadDTO creditPayloadDTO);

}
