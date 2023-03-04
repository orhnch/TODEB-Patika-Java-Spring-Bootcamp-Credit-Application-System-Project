package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.Role;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private List<Role> roles;

}