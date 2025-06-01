package com.example.rkr.enums;

public enum UserTypes {
    USER("user"),
    COMPANY("company");

    private final String value;

    UserTypes(String user) {
        this.value = user;
    }

    public String getValue() {
        return value;
    }

    public static UserTypes fromString(String value) {
        for (UserTypes type : UserTypes.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user type: " + value);
    }
}
