package com.packers.movers.server;

import com.packers.movers.commons.utils.Sequence;

public enum  ApplicationRole {
    ADMIN("admin"),
    USER("user");

    private final String value;

    ApplicationRole(String value) {
        this.value = value;
    }

    public String getStringValue() {
        return value;
    }

    public String[] getRoles() {
        return new String[] { value };
    }

    public static String[] getRoles(ApplicationRole... roles) {
        return Sequence.of(roles).map(ApplicationRole::getStringValue).toArray(String.class);
    }
}
