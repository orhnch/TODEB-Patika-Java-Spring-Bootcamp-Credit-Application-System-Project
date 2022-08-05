package com.todeb.patika.bootcamp.CreditApplicationSystem.exception;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException{


    public AlreadyExistException(String entityName, String cause) {
        super("Related customer who has National Number ID: " + entityName + cause + " Check this out!");
    }
}
