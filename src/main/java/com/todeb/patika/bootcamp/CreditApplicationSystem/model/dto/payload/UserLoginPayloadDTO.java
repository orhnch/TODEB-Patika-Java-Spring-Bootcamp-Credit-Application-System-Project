package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginPayloadDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}