package com.github.sbouclier;

import com.github.sbouclier.result.*;
import com.github.sbouclier.utils.StreamUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * HttpAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClientTest {

    private final static String KRAKEN_BASE_URL = "https://api.kraken.com";

    @Mock
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
    public void should_unmarshal_server_time_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/server_time.mock.json");
        HttpApiClient<ServerTimeResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.SERVER_TIME.getUrl(0)))
        ).thenReturn(mockResponseBody);

        ServerTimeResult result = client.callPublic(KRAKEN_BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);

        // Then
        assertThat(result.getResult(), hasProperty("unixtime", equalTo(1501271914L)));
        assertThat(result.getResult(), hasProperty("rfc1123", equalTo("Fri, 28 Jul 17 19:58:34 +0000")));

        verify(mockHttpJsonClient).executePublicQuery(eq(KRAKEN_BASE_URL), eq(KrakenApiMethod.SERVER_TIME.getUrl(0)));
    }

    @Test
    public void should_unmarshal_asset_information_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/asset_information.mock.json");
        HttpApiClient<AssetInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        AssetInformationResult.AssetInformation xetc = new AssetInformationResult.AssetInformation();
        xetc.setAlternateName("ETC");
        xetc.setAssetClass("currency");
        xetc.setDecimals((byte) 10);
        xetc.setDisplayDecimals((byte) 5);

        AssetInformationResult.AssetInformation xeth = new AssetInformationResult.AssetInformation();
        xeth.setAlternateName("ETH");
        xeth.setAssetClass("currency");
        xeth.setDecimals((byte) 10);
        xeth.setDisplayDecimals((byte) 5);

        AssetInformationResult.AssetInformation zeur = new AssetInformationResult.AssetInformation();
        zeur.setAlternateName("EUR");
        zeur.setAssetClass("currency");
        zeur.setDecimals((byte) 4);
        zeur.setDisplayDecimals((byte) 2);

        AssetInformationResult.AssetInformation zusd = new AssetInformationResult.AssetInformation();
        zusd.setAlternateName("USD");
        zusd.setAssetClass("currency");
        zusd.setDecimals((byte) 4);
        zusd.setDisplayDecimals((byte) 2);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.ASSET_INFORMATION.getUrl(0)))
        ).thenReturn(mockResponseBody.toString());
        AssetInformationResult result = client.callPublic(
                KRAKEN_BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class);

        // Then
        assertEquals(26, result.getResult().size());
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));

        verify(mockHttpJsonClient).executePublicQuery(eq(KRAKEN_BASE_URL), eq(KrakenApiMethod.ASSET_INFORMATION.getUrl(0)));
    }

    @Test
    public void should_unmarshal_asset_pairs_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs.mock.json");
        HttpApiClient<AssetPairsResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.ASSET_PAIRS.getUrl(0)))
        ).thenReturn(mockResponseBody.toString());
        AssetPairsResult result = client.callPublic(
                KRAKEN_BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);

        // Then
        AssetPairsResult.AssetPair pair = result.getResult().get("XETCXXBT");

        assertEquals(64, result.getResult().size());

        assertEquals("ETCXBT", pair.getAlternatePairName());
        assertEquals("currency", pair.getBaseAssetClass());
        assertEquals("XETC", pair.getBaseAssetId());
        assertEquals("currency", pair.getQuoteAssetClass());
        assertEquals("XXBT", pair.getQuoteAssetId());
        assertEquals("unit", pair.getLot());

        assertEquals(8, pair.getPairDecimals().intValue());
        assertEquals(8, pair.getLotDecimals().intValue());
        assertEquals(1, pair.getLotMultiplier().intValue());

        assertThat(pair.getLeverageBuy(), contains(2, 3));
        assertThat(pair.getLeverageSell(), contains(2, 3));

        assertThat(pair.getFees(), contains(
                new AssetPairsResult.AssetPair.Fee(0, 0.26f),
                new AssetPairsResult.AssetPair.Fee(50000, 0.24f),
                new AssetPairsResult.AssetPair.Fee(100000, 0.22f),
                new AssetPairsResult.AssetPair.Fee(250000, 0.2f),
                new AssetPairsResult.AssetPair.Fee(500000, 0.18f),
                new AssetPairsResult.AssetPair.Fee(1000000, 0.16f),
                new AssetPairsResult.AssetPair.Fee(2500000, 0.14f),
                new AssetPairsResult.AssetPair.Fee(5000000, 0.12f),
                new AssetPairsResult.AssetPair.Fee(10000000, 0.1f)
        ));

        assertThat(pair.getFeesMaker(), contains(
                new AssetPairsResult.AssetPair.Fee(0, 0.16f),
                new AssetPairsResult.AssetPair.Fee(50000, 0.14f),
                new AssetPairsResult.AssetPair.Fee(100000, 0.12f),
                new AssetPairsResult.AssetPair.Fee(250000, 0.1f),
                new AssetPairsResult.AssetPair.Fee(500000, 0.08f),
                new AssetPairsResult.AssetPair.Fee(1000000, 0.06f),
                new AssetPairsResult.AssetPair.Fee(2500000, 0.04f),
                new AssetPairsResult.AssetPair.Fee(5000000, 0.02f),
                new AssetPairsResult.AssetPair.Fee(10000000, 0f)
        ));

        assertEquals(80, pair.getMarginCall().intValue());
        assertEquals(40, pair.getMarginStop().intValue());

        verify(mockHttpJsonClient).executePublicQuery(eq(KRAKEN_BASE_URL), eq(KrakenApiMethod.ASSET_PAIRS.getUrl(0)));
    }

    @Test
    public void should_unmarshal_ticker_information_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/ticker_information.mock.json");
        HttpApiClient<TickerInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");
        TickerInformationResult result = client.callPublic(
                KRAKEN_BASE_URL,
                KrakenApiMethod.TICKER_INFORMATION,
                TickerInformationResult.class, params);

        // Then
        assertEquals(result.getResult().size(), 2);
        assertThat(BigDecimal.valueOf(157.49201), Matchers.comparesEqualTo(result.getResult().get("XETHZEUR").ask.price));
        assertThat(BigDecimal.valueOf(2352.76900), Matchers.comparesEqualTo(result.getResult().get("XXBTZEUR").ask.price));

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any());
    }

    @Test
    public void should_unmarshal_ohlc_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/ohlc.mock.json");
        HttpApiClient<OHLCResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        OHLCResult result = client.callPublic(KRAKEN_BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);

        // Then
        assertEquals(720, result.getOHLCData().size());
        assertEquals(result.getLast().intValue(), 1501200000);

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.OHLC.getUrl(0)),
                any());
    }

    @Test
    public void should_unmarshal_order_book_result() throws KrakenApiException, IOException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/order_book.mock.json");
        HttpApiClient<OrderBookResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.ORDER_BOOK.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("count", "3");
        OrderBookResult result = client.callPublic(KRAKEN_BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);

        // Then
        assertEquals(100, result.getResult().get("XXBTZEUR").asks.size());
        assertThat(result.getResult().get("XXBTZEUR").asks.get(0).price, Matchers.comparesEqualTo(BigDecimal.valueOf(2378.58700)));
        assertThat(result.getResult().get("XXBTZEUR").asks.get(0).volume, Matchers.comparesEqualTo(BigDecimal.valueOf(1.089)));
        assertEquals(result.getResult().get("XXBTZEUR").asks.get(0).timestamp.intValue(), 1501320458);
        assertThat(result.getResult().get("XXBTZEUR").asks.get(1).price, Matchers.comparesEqualTo(BigDecimal.valueOf(2378.96900)));
        assertThat(result.getResult().get("XXBTZEUR").asks.get(1).volume, Matchers.comparesEqualTo(BigDecimal.valueOf(0.022)));
        assertEquals(result.getResult().get("XXBTZEUR").asks.get(1).timestamp.intValue(), 1501320449);
        assertThat(result.getResult().get("XXBTZEUR").asks.get(2).price, Matchers.comparesEqualTo(BigDecimal.valueOf(2378.97100)));
        assertThat(result.getResult().get("XXBTZEUR").asks.get(2).volume, Matchers.comparesEqualTo(BigDecimal.valueOf(0.058)));
        assertEquals(result.getResult().get("XXBTZEUR").asks.get(2).timestamp.intValue(), 1501319911);

        verify(mockHttpJsonClient).executePublicQuery(
                eq(KRAKEN_BASE_URL),
                eq(KrakenApiMethod.ORDER_BOOK.getUrl(0)),
                any());
    }
}
