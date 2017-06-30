package com.github.sbouclier.result;

import java.util.ArrayList;

/**
 * Result wrapper to encapsulate http response
 *
 * @param <T> result response type
 * @author Stéphane Bouclier
 */
public class Result<T> {

    private ArrayList<Object> error = new ArrayList<>();
    private T result;

    /**
     * Get errors
     *
     * @return errors
     */
    public ArrayList<Object> getError() {
        return error;
    }

    /**
     * Get result
     *
     * @return result
     */
    public T getResult() {
        return result;
    }
}
