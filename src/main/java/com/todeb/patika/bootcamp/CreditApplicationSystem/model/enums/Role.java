package com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CUSTOMER;

    public String getAuthority() {
        return name();
    }
}
