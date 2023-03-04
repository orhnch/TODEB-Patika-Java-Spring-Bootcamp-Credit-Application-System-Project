package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class CustomerPayloadDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nationalNumberId;
    private String firstName;
    private String lastName;
    private int age;
    private int salary;
    private String phoneNumber;
}
