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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.samePropertyValuesAs;
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
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.SERVER_TIME.getUrl(0)))
        ).thenReturn(mockResponseBody);

        ServerTimeResult result = client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);

        // Then
        assertThat(result.getResult().unixtime, equalTo(1501271914L));
        assertThat(result.getResult().rfc1123, equalTo("Fri, 28 Jul 17 19:58:34 +0000"));

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.SERVER_TIME.getUrl(0)));
    }

    @Test
    public void should_unmarshal_asset_information_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/asset_information.mock.json");
        HttpApiClient<AssetInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        AssetInformationResult.AssetInformation xetc = new AssetInformationResult.AssetInformation();
        xetc.alternateName = "ETC";
        xetc.assetClass = "currency";
        xetc.decimals = (byte) 10;
        xetc.displayDecimals = (byte) 5;

        AssetInformationResult.AssetInformation xeth = new AssetInformationResult.AssetInformation();
        xeth.alternateName = "ETH";
        xeth.assetClass = "currency";
        xeth.decimals = (byte) 10;
        xeth.displayDecimals = (byte) 5;

        AssetInformationResult.AssetInformation zeur = new AssetInformationResult.AssetInformation();
        zeur.alternateName = "EUR";
        zeur.assetClass = "currency";
        zeur.decimals = (byte) 4;
        zeur.displayDecimals = (byte) 2;

        AssetInformationResult.AssetInformation zusd = new AssetInformationResult.AssetInformation();
        zusd.alternateName = "USD";
        zusd.assetClass = "currency";
        zusd.decimals = (byte) 4;
        zusd.displayDecimals = (byte) 2;

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ASSET_INFORMATION.getUrl(0)))
        ).thenReturn(mockResponseBody.toString());
        AssetInformationResult result = client.callPublic(
                KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class);

        // Then
        assertEquals(26, result.getResult().size());
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.ASSET_INFORMATION.getUrl(0)));
    }

    @Test
    public void should_unmarshal_asset_pairs_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs.mock.json");
        HttpApiClient<AssetPairsResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ASSET_PAIRS.getUrl(0)))
        ).thenReturn(mockResponseBody.toString());
        AssetPairsResult result = client.callPublic(
                KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);

        // Then
        AssetPairsResult.AssetPair pair = result.getResult().get("XETCXXBT");

        assertEquals(64, result.getResult().size());

        assertEquals("ETCXBT", pair.alternatePairName);
        assertEquals("currency", pair.baseAssetClass);
        assertEquals("XETC", pair.baseAssetId);
        assertEquals("currency", pair.quoteAssetClass);
        assertEquals("XXBT", pair.quoteAssetId);
        assertEquals("unit", pair.lot);

        assertEquals(8, pair.pairDecimals.intValue());
        assertEquals(8, pair.lotDecimals.intValue());
        assertEquals(1, pair.lotMultiplier.intValue());

        assertThat(pair.leverageBuy, contains(2, 3));
        assertThat(pair.leverageSell, contains(2, 3));

        assertThat(pair.fees, contains(
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

        assertThat(pair.feesMaker, contains(
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

        assertEquals(80, pair.marginCall.intValue());
        assertEquals(40, pair.marginStop.intValue());

        verify(mockHttpJsonClient).executePublicQuery(eq(KrakenAPIClient.BASE_URL), eq(KrakenApiMethod.ASSET_PAIRS.getUrl(0)));
    }

    @Test
    public void should_unmarshal_ticker_information_result() throws IOException, KrakenApiException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/ticker_information.mock.json");
        HttpApiClient<TickerInformationResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.TICKER_INFORMATION.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");
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
    public void should_unmarshal_order_book_result() throws KrakenApiException, IOException {

        // Given
        final String mockResponseBody = StreamUtils.getResourceAsString(this.getClass(), "json/order_book.mock.json");
        HttpApiClient<OrderBookResult> client = new HttpApiClient<>(mockHttpJsonClient);

        // When
        when(mockHttpJsonClient.executePublicQuery(
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDER_BOOK.getUrl(0)),
                any())
        ).thenReturn(mockResponseBody.toString());

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("count", "3");
        OrderBookResult result = client.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);

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
                eq(KrakenAPIClient.BASE_URL),
                eq(KrakenApiMethod.ORDER_BOOK.getUrl(0)),
                any());
    }
}
