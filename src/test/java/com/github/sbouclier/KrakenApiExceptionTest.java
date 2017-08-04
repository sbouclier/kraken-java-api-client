package com.github.sbouclier;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * KrakenApiException test
 *
 * @author Stéphane Bouclier
 */
public class KrakenApiExceptionTest {

    @Test
    public void should_get_error_message() {
        KrakenApiException ex = new KrakenApiException("error");

        assertThat(ex.getMessage(), equalTo("[error]"));
    }

    @Test
    public void should_get_error_message_with_throwable() {
        KrakenApiException ex = new KrakenApiException("error", new Exception());

        assertThat(ex.getMessage(), equalTo("[error]"));
    }

    @Test
    public void should_get_error_messages() {
        List<String> errors = new ArrayList<>();
        errors.add("error 1");
        errors.add("error 2");
        errors.add("error 3");

        KrakenApiException ex = new KrakenApiException(errors);

        assertThat(ex.getMessage(), equalTo("[error 1, error 2, error 3]"));
    }
}
