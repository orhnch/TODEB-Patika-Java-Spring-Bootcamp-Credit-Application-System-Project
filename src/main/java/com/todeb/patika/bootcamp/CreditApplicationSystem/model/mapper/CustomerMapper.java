package com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());
        customerDTO.setSalary(customer.getSalary());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
    }
    public static Customer toEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());
        customer.setSalary(customerDTO.getSalary());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return customer;
    }
}
