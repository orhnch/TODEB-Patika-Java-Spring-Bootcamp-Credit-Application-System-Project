package com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity;

public enum CreditLimitStage {
    LOWER_STAGE(10000),
    HIGHER_STAGE(20000);

    private final int value;


    CreditLimitStage(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
