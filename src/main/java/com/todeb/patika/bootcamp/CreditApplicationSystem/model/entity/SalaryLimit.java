package com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity;

public enum SalaryLimit {
    SALARY_LIMIT(5000);

    private final int value;


    SalaryLimit(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
