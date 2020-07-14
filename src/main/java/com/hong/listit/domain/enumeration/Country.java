package com.hong.listit.domain.enumeration;

/**
 * The Country enumeration.
 */
public enum Country {
    CA("Canada");

    private final String value;


    Country(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
