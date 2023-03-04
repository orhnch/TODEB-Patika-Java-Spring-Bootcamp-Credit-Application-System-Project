package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JwtResponse {
    private String accessToken;
    private long id;
    private String refreshToken;
}
