package com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums;

public enum CreditLimitMultiplier {
    CREDIT_LIMIT_MULTIPLIER(4);

    private final int value;

    CreditLimitMultiplier(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
