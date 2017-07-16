package com.github.sbouclier.result;

/**
 * Result wrapper with last id
 *
 * @param <T> result response type
 * @author Stéphane Bouclier
 */
public class ResultWithLastId<T> extends Result<T> {
    private Long lastId = 0L;

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }
}
