package com.github.sbouclier.result.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Order type
 *
 * @author Stéphane Bouclier
 */
public enum OrderType {
    MARKET("market"),
    LIMIT("limit"),
    STOP_LOSS("stop-loss"),
    TAKE_PROFIT("take-profit"),
    STOP_LOSS_PROFIT("stop-loss-profit"),
    STOP_LOSS_PROFIT_LIMIT("stop-loss-profit-limit"),
    STOP_LOSS_LIMIT("stop-loss-limit"),
    TAKE_PROFIT_LIMIT("take-profit-limit"),
    TRAILING_STOP("trailing-stop"),
    TRAILING_STOP_LIMIT("trailing-stop-limit"),
    STOP_LOSS_AND_LIMIT("stop-loss-and-limit"),
    SETTLE_POSITION("settle-position");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}