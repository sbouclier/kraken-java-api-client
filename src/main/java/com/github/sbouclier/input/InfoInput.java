package com.github.sbouclier.input;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Info input
 *
 * @author Stéphane Bouclier
 */
public enum InfoInput {

    ALL("info"),
    LEVERAGE("leverage"),
    FEES("fees"),
    MARGIN("margin");

    private String value;

    InfoInput(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
