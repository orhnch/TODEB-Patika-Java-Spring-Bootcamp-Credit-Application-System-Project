package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);

}
