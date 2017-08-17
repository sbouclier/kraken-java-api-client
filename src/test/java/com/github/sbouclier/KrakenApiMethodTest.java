package com.github.sbouclier;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * KrakenApiMethod test
 *
 * @author Stéphane Bouclier
 */
public class KrakenApiMethodTest {

    @Test
    public void should_return_url() {
        assertThat(KrakenApiMethod.SERVER_TIME.getUrl(0), equalTo("/0/public/Time"));
        assertThat(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0), equalTo("/0/private/Balance"));
    }

    @Test
    public void should_return_values() {
        assertThat(19, equalTo(KrakenApiMethod.values().length));
    }

    @Test
    public void should_return_value() {
        assertThat(KrakenApiMethod.valueOf("SERVER_TIME"), equalTo(KrakenApiMethod.SERVER_TIME));
    }
}
