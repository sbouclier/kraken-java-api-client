package com.github.sbouclier;

import com.github.sbouclier.result.*;
import com.github.sbouclier.utils.StreamUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * HttpAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClientTest {

    private HttpJsonClient mockHttpJsonClient;

    @Before
    public void setUp() throws IOException {
        mockHttpJsonClient = mock(HttpJsonClient.class);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mockHttpJsonClient);
    }

    @Test
    public void should_call_valid_public_method() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/server_time.mock.json");
        HttpApiClient<ServerTimeResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.SERVER_TIME.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        ServerTimeResult result = client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);

        // Then
        assertThat(result.getResult().unixtime, equalTo(1501271914L));
        assertThat(result.getResult().rfc1123, equalTo("Fri, 28 Jul 17 19:58:34 +0000"));

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.SERVER_TIME.getUrl(0)));
    }

    @Test
    public void should_call_invalid_public_method_with_error() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments.mock.json");
        HttpApiClient<ServerTimeResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.SERVER_TIME.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        try {
            client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[EGeneral:Invalid arguments]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.SERVER_TIME.getUrl(0)));
    }

    @Test
    public void should_call_invalid_public_method_with_unmarshalled_response() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = "";
        HttpApiClient<ServerTimeResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.SERVER_TIME.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        try {
            client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to query Kraken API]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.SERVER_TIME.getUrl(0)));
    }

    @Test
    public void should_call_valid_public_method_with_params() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/ticker_information.mock.json");
        HttpApiClient<TickerInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");

        // When
        TickerInformationResult result = client.callPublic(
                KrakenAPIClient.BASE_URL,
                KrakenApiMethod.TICKER_INFORMATION,
                TickerInformationResult.class, params);

        // Then
        assertEquals(result.getResult().size(), 2);
        assertThat(BigDecimal.valueOf(157.49201), Matchers.comparesEqualTo(result.getResult().get("XETHZEUR").ask.price));
        assertThat(BigDecimal.valueOf(2352.76900), Matchers.comparesEqualTo(result.getResult().get("XXBTZEUR").ask.price));

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_public_method_with_params_and_error() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments.mock.json");
        HttpApiClient<TickerInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");

        // When
        try {
            client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[EGeneral:Invalid arguments]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_public_method_with_params_and_unmarshalled_response() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = "";
        HttpApiClient<TickerInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");

        // When
        try {
            client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to query Kraken API]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_call_valid_public_method_with_last_id() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/ohlc.mock.json");
        HttpApiClient<OHLCResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        OHLCResult result = client.callPublicWithLastId(
                KrakenAPIClient.BASE_URL,
                KrakenApiMethod.OHLC,
                OHLCResult.class, params);

        // Then
        assertEquals(720, result.getResult().get("XXBTZEUR").size());
        assertEquals(result.getLastId().intValue(), 1501200000);

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_public_method_with_unextractable_last_id() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments.mock.json");
        HttpApiClient<OHLCResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        try {
            client.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to extract last id]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_public_method_with_last_id_and_unmarshalled_response() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/unmarshalled_last_id.mock.json");
        HttpApiClient<OHLCResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        try {
            client.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to query Kraken API]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_public_method_with_last_id_and_error() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments_last_id.mock.json");
        HttpApiClient<OHLCResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        try {
            client.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[EGeneral:Invalid arguments]"));
        }

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any());
    }

    @Test
    public void should_call_valid_private_method() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/account_balance.mock.json");
        HttpApiClient<AccountBalanceResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        AccountBalanceResult result = client.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);

        // Then
        assertThat(result.getResult().get("ZEUR"), Matchers.comparesEqualTo(BigDecimal.valueOf(86.1602)));
        assertThat(result.getResult().get("XXBT"), Matchers.comparesEqualTo(BigDecimal.valueOf(0.0472043520)));
        assertThat(result.getResult().get("XXRP"), Matchers.comparesEqualTo(BigDecimal.valueOf(100)));
        assertThat(result.getResult().get("BCH"), Matchers.comparesEqualTo(BigDecimal.valueOf(0.0472043520)));

        verify(mockHttpJsonClient).executePrivateQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)));
    }

    @Test
    public void should_call_invalid_private_method_with_error() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments.mock.json");
        HttpApiClient<AccountBalanceResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        try {
            client.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[EGeneral:Invalid arguments]"));
        }

        verify(mockHttpJsonClient).executePrivateQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)));
    }

    @Test
    public void should_call_invalid_private_method_with_unmarshalled_response() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = "";
        HttpApiClient<AccountBalanceResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)))
        ).thenReturn(mockResponseBody);

        // When
        try {
            client.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to query Kraken API]"));
        }

        verify(mockHttpJsonClient).executePrivateQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.ACCOUNT_BALANCE.getUrl(0)));
    }

    @Test
    public void should_call_valid_private_method_with_params() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/orders_information.mock.json");
        HttpApiClient<OrdersInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("txid", "OGRQC4-Q5C5N-2EYZDZ");

        // When
        OrdersInformationResult result = client.callPrivate(
                KrakenAPIClient.BASE_URL,
                KrakenApiMethod.ORDERS_INFORMATION,
                OrdersInformationResult.class, params);

        // Then
        assertThat(result.getResult().size(), equalTo(1));
        assertThat(result.getResult().get("OGRQC4-Q5C5N-2EYZDZ").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2100)));

        verify(mockHttpJsonClient).executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_private_method_with_params_and_error() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/invalid_arguments.mock.json");
        HttpApiClient<OrdersInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("txid", "OGRQC4-Q5C5N-2EYZDZ");

        // When
        try {
            client.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[EGeneral:Invalid arguments]"));
        }

        verify(mockHttpJsonClient).executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_call_invalid_private_method_with_params_and_unmarshalled_response() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = "";
        HttpApiClient<OrdersInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        when(mockHttpJsonClient.executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("txid", "OGRQC4-Q5C5N-2EYZDZ");

        // When
        try {
            client.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);

            fail();
        } catch (KrakenApiException ex) {
            assertThat(ex.getMessage(), equalTo("[unable to query Kraken API]"));
        }

        verify(mockHttpJsonClient).executePrivateQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDERS_INFORMATION.getUrl(0)),
                any());
    }
}
