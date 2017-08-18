package com.github.sbouclier.result.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Order direction Buy/Sell
 *
 * @author Stéphane Bouclier
 */
public enum OrderDirection {

    BUY("buy"),
    SELL("sell");

    private String value;

    OrderDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
