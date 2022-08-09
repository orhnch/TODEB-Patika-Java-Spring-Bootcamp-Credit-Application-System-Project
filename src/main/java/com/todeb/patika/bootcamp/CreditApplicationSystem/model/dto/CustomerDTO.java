package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String nationalNumberId;
    private String firstName;
    private String lastName;
    private int age;
    private int salary;
    private String phoneNumber;
}
