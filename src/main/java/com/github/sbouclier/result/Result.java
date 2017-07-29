package com.github.sbouclier.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;

/**
 * Result wrapper to encapsulate http response
 *
 * @param <T> result response type
 * @author Stéphane Bouclier
 */
public class Result<T> {

    private ArrayList<String> error = new ArrayList<>();
    private T result;

    /**
     * Get errors
     *
     * @return errors
     */
    public ArrayList<String> getError() {
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

    /**
     * Set result
     *
     * @param result
     */
    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("error", error)
                .append("result", result)
                .toString();
    }
}
