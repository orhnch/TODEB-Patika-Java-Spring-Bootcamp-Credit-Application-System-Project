package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.CustomerPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerPayloadDTO toDTO(Customer customer);

    Customer toEntity(CustomerPayloadDTO customerPayloadDTO);

}
