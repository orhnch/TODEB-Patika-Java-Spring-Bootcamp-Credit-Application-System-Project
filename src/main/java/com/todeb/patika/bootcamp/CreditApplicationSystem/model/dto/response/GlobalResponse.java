package com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object data;
}
