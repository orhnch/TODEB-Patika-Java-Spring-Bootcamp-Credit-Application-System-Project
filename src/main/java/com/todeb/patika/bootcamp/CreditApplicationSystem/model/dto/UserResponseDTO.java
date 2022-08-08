package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private List<Role> roles;

}