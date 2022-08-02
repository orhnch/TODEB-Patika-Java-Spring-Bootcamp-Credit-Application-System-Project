package com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity;

public enum CreditScoreLimit {
    LOWER(500),
    HIGHER(1000);
    private final int value;


    CreditScoreLimit(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
