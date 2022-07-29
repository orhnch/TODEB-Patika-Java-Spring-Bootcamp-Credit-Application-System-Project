package com.todeb.patika.bootcamp.CreditApplicationSystem.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException {
    private String details;

    public EntityNotFoundException(String entityName, String cause) {
        super("Related " + entityName + " not found with : [" + cause + "]");
        details = "Some Special Details";
    }
}