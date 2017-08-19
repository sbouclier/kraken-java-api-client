package com.github.sbouclier;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * HttpApiClientFactory test
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClientFactoryTest {

    @Test
    public void should_return_public_http_api_client() {
        HttpApiClientFactory factory = new HttpApiClientFactory();

        assertThat(factory.getHttpApiClient(KrakenApiMethod.SERVER_TIME), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.ASSET_PAIRS), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.TICKER_INFORMATION), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.OHLC), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.ORDER_BOOK), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.RECENT_TRADES), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient(KrakenApiMethod.RECENT_SPREADS), instanceOf(HttpApiClient.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknown_method_should_throws_exception_for_public_http_api_client() {
        HttpApiClientFactory factory = new HttpApiClientFactory();
        factory.getHttpApiClient(KrakenApiMethod.ACCOUNT_BALANCE);
    }

    @Test
    public void should_return_private_http_api_client() {
        HttpApiClientFactory factory = new HttpApiClientFactory();

        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.ACCOUNT_BALANCE), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.TRADE_BALANCE), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.OPEN_ORDERS), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.CLOSED_ORDERS), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.ORDERS_INFORMATION), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.TRADES_HISTORY), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.TRADES_INFORMATION), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.OPEN_POSITIONS), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.LEDGERS_INFORMATION), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.QUERY_LEDGERS), instanceOf(HttpApiClient.class));
        assertThat(factory.getHttpApiClient("key", "secret",KrakenApiMethod.TRADE_VOLUME), instanceOf(HttpApiClient.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknown_method_should_throws_exception_for_private_http_api_client() {
        HttpApiClientFactory factory = new HttpApiClientFactory();
        factory.getHttpApiClient("key", "secret", KrakenApiMethod.SERVER_TIME);
    }
}
