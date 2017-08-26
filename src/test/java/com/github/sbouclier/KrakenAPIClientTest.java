package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.input.InfoInput;
import com.github.sbouclier.input.Interval;
import com.github.sbouclier.mock.MockInitHelper;
import com.github.sbouclier.result.*;
import com.github.sbouclier.utils.StreamUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
    public void should_return_all_asset_information() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/assets_information.mock.json");
        AssetsInformationResult mockResult = new ObjectMapper().readValue(jsonResult, AssetsInformationResult.class);

        AssetsInformationResult.AssetInformation xetc = new AssetsInformationResult.AssetInformation();
        xetc.alternateName = "ETC";
        xetc.assetClass = "currency";
        xetc.decimals = (byte) 10;
        xetc.displayDecimals = (byte) 5;

        AssetsInformationResult.AssetInformation xeth = new AssetsInformationResult.AssetInformation();
        xeth.alternateName = "ETH";
        xeth.assetClass = "currency";
        xeth.decimals = (byte) 10;
        xeth.displayDecimals = (byte) 5;

        AssetsInformationResult.AssetInformation zeur = new AssetsInformationResult.AssetInformation();
        zeur.alternateName = "EUR";
        zeur.assetClass = "currency";
        zeur.decimals = (byte) 4;
        zeur.displayDecimals = (byte) 2;

        AssetsInformationResult.AssetInformation zusd = new AssetsInformationResult.AssetInformation();
        zusd.alternateName = "USD";
        zusd.assetClass = "currency";
        zusd.decimals = (byte) 4;
        zusd.displayDecimals = (byte) 2;

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetsInformationResult result = client.getAssetsInformation();

        // Then
        assertEquals(26, result.getResult().size());
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class);
    }

    @Test
    public void should_return_some_asset_information() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/assets_information_eur_eth.mock.json");
        AssetsInformationResult mockResult = new ObjectMapper().readValue(jsonResult, AssetsInformationResult.class);

        AssetsInformationResult.AssetInformation xeth = new AssetsInformationResult.AssetInformation();
        xeth.alternateName = "ETH";
        xeth.assetClass = "currency";
        xeth.decimals = (byte) 10;
        xeth.displayDecimals = (byte) 5;

        AssetsInformationResult.AssetInformation zeur = new AssetsInformationResult.AssetInformation();
        zeur.alternateName = "EUR";
        zeur.assetClass = "currency";
        zeur.decimals = (byte) 4;
        zeur.displayDecimals = (byte) 2;

        Map<String, String> params = new HashMap<>();
        params.put("asset", "ZEUR,ZETH");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetsInformationResult result = client.getAssetsInformation("ZEUR", "ZETH");

        // Then
        assertEquals(2, result.getResult().size());
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params);
    }

    @Test
    public void should_return_all_asset_pairs() throws IOException, KrakenApiException {

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
    public void should_return_some_asset_pairs() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs_margin_etheur_xbteur.mock.json");
        AssetPairsResult mockResult = new ObjectMapper().readValue(jsonResult, AssetPairsResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("info", "margin");
        params.put("pair", "XETHZEUR,XXBTZEUR");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_PAIRS)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetPairsResult result = client.getAssetPairs(InfoInput.MARGIN, "XETHZEUR", "XXBTZEUR");

        // Then
        AssetPairsResult.AssetPair ethEur = result.getResult().get("XETHZEUR");
        AssetPairsResult.AssetPair xbtEUr = result.getResult().get("XXBTZEUR");

        assertEquals(80, ethEur.marginCall.intValue());
        assertEquals(40, ethEur.marginLevel.intValue());
        assertEquals(80, xbtEUr.marginCall.intValue());
        assertEquals(40, xbtEUr.marginLevel.intValue());

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_PAIRS);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params);
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
        OHLCResult mockResult = MockInitHelper.buildOHLCResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.OHLC)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OHLCResult result = client.getOHLC("BTCEUR", Interval.ONE_DAY);

        // Then
        assertEquals(2, result.getResult().get("XXBTZEUR").size());
        assertEquals(result.getLastId().intValue(), 23456);

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.OHLC);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    @Test
    public void should_return_ohlc_since_id() throws IOException, KrakenApiException {

        // Given
        OHLCResult mockResult = MockInitHelper.buildOHLCResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("interval", "1440");
        params.put("since", "123456");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.OHLC)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OHLCResult result = client.getOHLC("BTCEUR", Interval.ONE_DAY, 123456);

        // Then
        assertEquals(2, result.getResult().get("XXBTZEUR").size());
        assertEquals(result.getLastId().intValue(), 23456);

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.OHLC);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    @Test
    public void should_return_order_book() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/order_book.mock.json");
        OrderBookResult mockResult = new ObjectMapper().readValue(jsonResult, OrderBookResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ORDER_BOOK)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OrderBookResult result = client.getOrderBook("BTCEUR");

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

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ORDER_BOOK);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    @Test
    public void should_return_order_book_limited() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/order_book.mock.json");
        OrderBookResult mockResult = new ObjectMapper().readValue(jsonResult, OrderBookResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("count", "3");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ORDER_BOOK)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        OrderBookResult result = client.getOrderBook("BTCEUR", 3);

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

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ORDER_BOOK);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    @Test
    public void should_return_recent_trades() throws IOException, KrakenApiException {

        // Given
        RecentTradeResult mockResult = MockInitHelper.buildRecentTradeResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.RECENT_TRADES)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        RecentTradeResult result = client.getRecentTrades("BTCEUR");

        // Then
        List<RecentTradeResult.RecentTrade> resultTrades = result.getResult().get("XXBTZEUR");
        assertEquals(2, resultTrades.size());
        assertThat(resultTrades.get(0).price, Matchers.comparesEqualTo(BigDecimal.TEN));
        assertThat(resultTrades.get(0).volume, Matchers.comparesEqualTo(BigDecimal.ONE));
        assertThat(resultTrades.get(1).price, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(resultTrades.get(1).volume, Matchers.comparesEqualTo(BigDecimal.valueOf(2)));
        assertEquals(123456L, result.getLastId().longValue());

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.RECENT_TRADES);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    @Test
    public void should_return_recent_trades_since_id() throws IOException, KrakenApiException {

        // Given
        RecentTradeResult mockResult = MockInitHelper.buildRecentTradeResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("since", "123456");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.RECENT_TRADES)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        RecentTradeResult result = client.getRecentTrades("BTCEUR", 123456);

        // Then
        List<RecentTradeResult.RecentTrade> resultTrades = result.getResult().get("XXBTZEUR");
        assertEquals(2, resultTrades.size());
        assertThat(resultTrades.get(0).price, Matchers.comparesEqualTo(BigDecimal.TEN));
        assertThat(resultTrades.get(0).volume, Matchers.comparesEqualTo(BigDecimal.ONE));
        assertThat(resultTrades.get(1).price, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(resultTrades.get(1).volume, Matchers.comparesEqualTo(BigDecimal.valueOf(2)));
        assertEquals(123456L, result.getLastId().longValue());

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.RECENT_TRADES);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    @Test
    public void should_return_recent_spread() throws IOException, KrakenApiException {
        // Given
        RecentSpreadResult mockResult = MockInitHelper.buildRecentSpreadResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.RECENT_SPREADS)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        RecentSpreadResult result = client.getRecentSpreads("BTCEUR");

        // Then
        List<RecentSpreadResult.Spread> resultSpreads = result.getResult().get("XXBTZEUR");
        assertEquals(2, resultSpreads.size());
        assertThat(resultSpreads.get(0).time, Matchers.comparesEqualTo(1));
        assertThat(resultSpreads.get(0).bid, Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
        assertThat(resultSpreads.get(0).ask, Matchers.comparesEqualTo(BigDecimal.valueOf(11)));
        assertThat(resultSpreads.get(1).time, Matchers.comparesEqualTo(2));
        assertThat(resultSpreads.get(1).bid, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(resultSpreads.get(1).ask, Matchers.comparesEqualTo(BigDecimal.valueOf(21)));
        assertEquals(123456L, result.getLastId().longValue());

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.RECENT_SPREADS);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    @Test
    public void should_return_recent_spread_since_id() throws IOException, KrakenApiException {
        // Given
        RecentSpreadResult mockResult = MockInitHelper.buildRecentSpreadResult();

        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTCEUR");
        params.put("since", "123456");

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.RECENT_SPREADS)).thenReturn(mockClient);
        when(mockClient.callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        RecentSpreadResult result = client.getRecentSpreads("BTCEUR", 123456);

        // Then
        List<RecentSpreadResult.Spread> resultSpreads = result.getResult().get("XXBTZEUR");
        assertEquals(2, resultSpreads.size());
        assertThat(resultSpreads.get(0).time, Matchers.comparesEqualTo(1));
        assertThat(resultSpreads.get(0).bid, Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
        assertThat(resultSpreads.get(0).ask, Matchers.comparesEqualTo(BigDecimal.valueOf(11)));
        assertThat(resultSpreads.get(1).time, Matchers.comparesEqualTo(2));
        assertThat(resultSpreads.get(1).bid, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(resultSpreads.get(1).ask, Matchers.comparesEqualTo(BigDecimal.valueOf(21)));
        assertEquals(123456L, result.getLastId().longValue());

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.RECENT_SPREADS);
        verify(mockClient).callPublicWithLastId(KrakenAPIClient.BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    @Test
    public void should_return_account_balance() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/account_balance.mock.json");
        AccountBalanceResult mockResult = new ObjectMapper().readValue(jsonResult, AccountBalanceResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.ACCOUNT_BALANCE)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        AccountBalanceResult result = client.getAccountBalance();

        // Then
        assertThat(result.getResult().get("ZEUR"), Matchers.comparesEqualTo(BigDecimal.valueOf(86.1602)));
        assertThat(result.getResult().get("XXBT"), Matchers.comparesEqualTo(BigDecimal.valueOf(0.0472043520)));
        assertThat(result.getResult().get("XXRP"), Matchers.comparesEqualTo(BigDecimal.valueOf(100)));
        assertThat(result.getResult().get("BCH"), Matchers.comparesEqualTo(BigDecimal.valueOf(0.0472043520)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.ACCOUNT_BALANCE);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    @Test
    public void should_return_trade_balance() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/trade_balance.mock.json");
        TradeBalanceResult mockResult = new ObjectMapper().readValue(jsonResult, TradeBalanceResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADE_BALANCE)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        TradeBalanceResult result = client.getTradeBalance();

        assertThat(result.getResult().equivalentBalance, Matchers.comparesEqualTo(BigDecimal.valueOf(260.1645)));
        assertThat(result.getResult().tradeBalance, Matchers.comparesEqualTo(BigDecimal.valueOf(230.6645)));
        assertThat(result.getResult().marginAmount, Matchers.comparesEqualTo(BigDecimal.ZERO));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADE_BALANCE);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    @Test
    public void should_return_open_orders() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/open_orders.mock.json");
        OpenOrdersResult mockResult = new ObjectMapper().readValue(jsonResult, OpenOrdersResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.OPEN_ORDERS)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        OpenOrdersResult result = client.getOpenOrders();

        assertThat(result.getResult().open.size(), equalTo(3));
        assertThat(result.getResult().open.get("OC6Z5B-NLAHB-6MQNLA").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2600)));
        assertThat(result.getResult().open.get("ORGIM4-6TDSR-DZMIID").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2700)));
        assertThat(result.getResult().open.get("OBP2WQ-RLHY2-OUZ5EA").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2500)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.OPEN_ORDERS);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    @Test
    public void should_return_closed_orders() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/closed_orders.mock.json");
        ClosedOrdersResult mockResult = new ObjectMapper().readValue(jsonResult, ClosedOrdersResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.CLOSED_ORDERS)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        ClosedOrdersResult result = client.getClosedOrders();

        assertThat(result.getResult().closed.size(), equalTo(49));
        assertThat(result.getResult().closed.get("OGRQC4-Q5C5N-2EYZDP").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2100)));
        assertThat(result.getResult().closed.get("ORDWRN-QH4LD-Y2KG3W").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2090)));
        assertThat(result.getResult().closed.get("OJUIIP-3AR2S-GTW2VU").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(1810)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.CLOSED_ORDERS);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    @Test
    public void should_return_orders_information() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/orders_information.mock.json");
        OrdersInformationResult mockResult = new ObjectMapper().readValue(jsonResult, OrdersInformationResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("txid", "OGRQC4-Q5C5N-2EYZDZ");

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.ORDERS_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        OrdersInformationResult result = client.getOrdersInformation(Arrays.asList("OGRQC4-Q5C5N-2EYZDZ"));

        assertThat(result.getResult().size(), equalTo(1));
        assertThat(result.getResult().get("OGRQC4-Q5C5N-2EYZDZ").description.price, Matchers.comparesEqualTo(BigDecimal.valueOf(2100)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.ORDERS_INFORMATION);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    @Test
    public void should_return_trades_history() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/trades_history.mock.json");
        TradesHistoryResult mockResult = new ObjectMapper().readValue(jsonResult, TradesHistoryResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADES_HISTORY)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        TradesHistoryResult result = client.getTradesHistory();

        assertThat(result.getResult().count, equalTo(51L));
        assertThat(result.getResult().trades.get("TICACU-DPSCI-EJ7FIR").price, Matchers.comparesEqualTo(BigDecimal.valueOf(2030)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADES_HISTORY);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class);
    }

    @Test
    public void should_return_trades_information() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/trades_information.mock.json");
        TradesInformationResult mockResult = new ObjectMapper().readValue(jsonResult, TradesInformationResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("txid", "TBKW74-IIBSM-LPZRWW,TW2JUT-MIK3P-RML5VC");

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADES_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        TradesInformationResult result = client.getTradesInformation(Arrays.asList("TBKW74-IIBSM-LPZRWW", "TW2JUT-MIK3P-RML5VC"));

        assertThat(result.getResult().size(), equalTo(2));
        assertThat(result.getResult().get("TBKW74-IIBSM-LPZRWW").orderTransactionId, equalTo("OW7A4A-JN5HV-MWCF3H"));
        assertThat(result.getResult().get("TW2JUT-MIK3P-RML5VC").orderTransactionId, equalTo("OK7VVF-P26QM-JP4SVX"));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADES_INFORMATION);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params);
    }

    @Test
    public void should_return_open_positions() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/open_positions.mock.json");
        OpenPositionsResult mockResult = new ObjectMapper().readValue(jsonResult, OpenPositionsResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("txid", "TY3TFI-KXBN3-LEICZJ");

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.OPEN_POSITIONS)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        OpenPositionsResult result = client.getOpenPositions(Arrays.asList("TY3TFI-KXBN3-LEICZJ"));

        assertThat(result.getResult().size(), equalTo(1));
        assertThat(result.getResult().get("TY3TFI-KXBN3-LEICZJ").orderTransactionId, equalTo("OCNUXJ-FET73-L2AZFR"));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.OPEN_POSITIONS);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params);
    }

    @Test
    public void should_return_ledgers_information() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ledgers_information.mock.json");
        LedgersInformationResult mockResult = new ObjectMapper().readValue(jsonResult, LedgersInformationResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.LEDGERS_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        LedgersInformationResult result = client.getLedgersInformation();

        assertThat(result.getResult().count, equalTo(124L));
        assertThat(result.getResult().ledger.get("LKHYSJ-DXPLB-VDMZAL").referenceId, equalTo("TFS77K-XLVZ2-C7OO5I"));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.LEDGERS_INFORMATION);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class);
    }

    @Test
    public void should_return_ledgers() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ledgers.mock.json");
        LedgersResult mockResult = new ObjectMapper().readValue(jsonResult, LedgersResult.class);

        Map<String, String> params = new HashMap<>();
        params.put("id", "LKHYSJ-DXPLB-VDMZAL,LZTMUA-6Q3X3-HBFNXO");

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.QUERY_LEDGERS)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        LedgersResult result = client.getLedgers(Arrays.asList("LKHYSJ-DXPLB-VDMZAL", "LZTMUA-6Q3X3-HBFNXO"));

        assertThat(result.getResult().size(), equalTo(2));
        assertThat(result.getResult().get("LKHYSJ-DXPLB-VDMZAL").referenceId, equalTo("TFS77K-XLVZ2-C7OO5I"));
        assertThat(result.getResult().get("LZTMUA-6Q3X3-HBFNXO").referenceId, equalTo("TY3TFI-KXBN3-LEICZJ"));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.QUERY_LEDGERS);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params);
    }

    @Test
    public void should_return_trade_volume() throws IOException, KrakenApiException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/trade_volume.mock.json");
        TradeVolumeResult mockResult = new ObjectMapper().readValue(jsonResult, TradeVolumeResult.class);

        // When
        when(mockClientFactory.getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADE_VOLUME)).thenReturn(mockClient);
        when(mockClient.callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient("apiKey", "apiSecret", mockClientFactory);
        TradeVolumeResult result = client.getTradeVolume();

        assertThat(result.getResult().currency, equalTo("ZUSD"));
        assertThat(result.getResult().volume, equalTo(BigDecimal.valueOf(773.2808)));

        verify(mockClientFactory).getHttpApiClient("apiKey", "apiSecret", KrakenApiMethod.TRADE_VOLUME);
        verify(mockClient).callPrivate(KrakenAPIClient.BASE_URL, KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class);
    }
}