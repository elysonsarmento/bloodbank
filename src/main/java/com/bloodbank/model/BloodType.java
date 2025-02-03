package com.bloodbank.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodType {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String symbol;

    BloodType(String symbol) {
        this.symbol = symbol;
    }

    @JsonValue
    public String getSymbol() {
        return symbol;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static BloodType fromSymbol(String symbol) {
        for (BloodType bloodType : BloodType.values()) {
            if (bloodType.symbol.equals(symbol)) {
                return bloodType;
            }
        }
        throw new IllegalArgumentException("Tipo sanguíneo inválido: " + symbol);
    }
}