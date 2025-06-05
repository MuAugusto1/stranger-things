package com.explorer.stranger_things.dto;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String value) {
        try {
            return Gender.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Gênero inválido. Use 'Male' ou 'Female'.");
        }
    }
}