package com.example.rkr.enums;

import androidx.annotation.NonNull;

public enum QuantityItems {
    ITEM("шт."),
    PACKAGE("уп."),
    LITER("л"),
    GRAM("г");


    private final String value;

    QuantityItems(String field) {
        this.value = field;
    }


    public String getValue() {
        return value;
    }

    public static QuantityItems fromString(String value) {
        for (QuantityItems type : QuantityItems.values()) {
            if (type.value.equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user type: " + value);
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
