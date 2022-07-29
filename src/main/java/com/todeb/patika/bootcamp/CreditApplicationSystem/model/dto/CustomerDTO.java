package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private String firstName;
    private String lastName;
    private int age;
    private int salary;
    private String phoneNumber;
}
