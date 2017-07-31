package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.result.*;
import com.github.sbouclier.utils.StreamUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * KrakenAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClientTest {

    private HttpApiClientFactory mockClientFactory;
    private HttpApiClient mockClient;

    @Before
    public void setUp() throws IOException {
        this.mockClientFactory = mock(HttpApiClientFactory.class);
        this.mockClient = mock(HttpApiClient.class);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mockClientFactory, mockClient);
    }

    @Test
    public void should_return_server_time() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/server_time.mock.json");
        ServerTimeResult mockResult = new ObjectMapper().readValue(jsonResult, ServerTimeResult.class);

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.SERVER_TIME)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        ServerTimeResult result = client.getServerTime();
        ServerTimeResult.ServerTime serverTime = result.getResult();

        // Then
        assertThat(serverTime.unixtime, equalTo(1501271914L));
        assertThat(serverTime.rfc1123, equalTo("Fri, 28 Jul 17 19:58:34 +0000"));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.SERVER_TIME);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    @Test
    public void should_return_asset_information() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_information.mock.json");
        AssetInformationResult mockResult = new ObjectMapper().readValue(jsonResult, AssetInformationResult.class);

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
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetInformationResult result = client.getAssetInformation();

        // Then
        assertEquals(26, result.getResult().size());
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class);
    }

    @Test
    public void should_return_asset_pairs() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs.mock.json");
        AssetPairsResult mockResult = new ObjectMapper().readValue(jsonResult, AssetPairsResult.class);

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_PAIRS)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetPairsResult result = client.getAssetPairs();

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

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_PAIRS);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    @Test
    public void should_return_ticker_information() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ticker_information.mock.json");
        TickerInformationResult mockResult = new ObjectMapper().readValue(jsonResult, TickerInformationResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR,ETHEUR");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.TICKER_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));

        // Then
        assertEquals(result.getResult().size(), 2);
        assertThat(BigDecimal.valueOf(157.49201), Matchers.comparesEqualTo(result.getResult().get("XETHZEUR").ask.price));
        assertThat(BigDecimal.valueOf(2352.76900), Matchers.comparesEqualTo(result.getResult().get("XXBTZEUR").ask.price));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.TICKER_INFORMATION);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }

    @Test
    public void should_return_ohlc() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ohlc.mock.json");
        OHLCResult mockResult = new ObjectMapper().readValue(jsonResult, OHLCResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.OHLC)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OHLCResult result = client.getOHLC("BTCEUR", KrakenAPIClient.Interval.ONE_DAY);

        // Then
        assertEquals(720, result.getOHLCData().size());
        assertEquals(result.getLast().intValue(), 1501200000);

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.OHLC);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    @Test
    public void should_return_ohlc_since_id() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ohlc.mock.json");
        OHLCResult mockResult = new ObjectMapper().readValue(jsonResult, OHLCResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");
        params.put("since", "123456");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.OHLC)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OHLCResult result = client.getOHLC("BTCEUR", KrakenAPIClient.Interval.ONE_DAY, 123456);

        // Then
        assertEquals(720, result.getOHLCData().size());
        assertEquals(result.getLast().intValue(), 1501200000);

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.OHLC);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }
}
