package com.github.sbouclier;

import java.util.ArrayList;
import java.util.List;

/**
 * Kraken API exception
 *
 * @author Stéphane Bouclier
 */
public class KrakenApiException extends Exception {

    List<String> errors = new ArrayList<>();

    public KrakenApiException(String error) {
        this.addError(error);
    }

    public KrakenApiException(List<String> errors) {
        this.errors = errors;
    }

    public KrakenApiException(String message, Throwable cause) {
        super(message, cause);
        this.addError(message);
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public String getMessage() {
        return errors.toString();
    }
}
