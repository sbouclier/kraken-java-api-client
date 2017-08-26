package com.github.sbouclier;

import com.github.sbouclier.input.InfoInput;
import com.github.sbouclier.input.Interval;
import com.github.sbouclier.result.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Kraken API client
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClient {

    public static String BASE_URL = "https://api.kraken.com";

    private HttpApiClientFactory clientFactory;

    private String apiKey;
    private String apiSecret;

    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    /**
     * Default constructor to call public API requests
     */
    public KrakenAPIClient() {
        this.clientFactory = new HttpApiClientFactory();
    }

    /**
     * Secure constructor to call private API requests
     *
     * @param apiKey
     * @param apiSecret
     */
    public KrakenAPIClient(String apiKey, String apiSecret) {
        this();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * Constructor injecting {@link com.github.sbouclier.HttpApiClientFactory}
     *
     * @param clientFactory
     */
    public KrakenAPIClient(HttpApiClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Constructor injecting api key, secret and {@link com.github.sbouclier.HttpApiClientFactory}
     *
     * @param clientFactory
     */
    public KrakenAPIClient(String apiKey, String apiSecret, HttpApiClientFactory clientFactory) {
        this(apiKey, apiSecret);
        this.clientFactory = clientFactory;
    }

    // -----------
    // - METHODS -
    // -----------

    /**
     * Get server time
     *
     * @return server time
     * @throws KrakenApiException
     */
    public ServerTimeResult getServerTime() throws KrakenApiException {
        HttpApiClient<ServerTimeResult> client = (HttpApiClient<ServerTimeResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.SERVER_TIME);
        return client.callPublic(BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    /**
     * Get assets information
     *
     * @return assets information
     * @throws KrakenApiException
     */
    public AssetsInformationResult getAssetsInformation() throws KrakenApiException {
        HttpApiClient<AssetsInformationResult> client = (HttpApiClient<AssetsInformationResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);
        return client.callPublic(BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class);
    }

    /**
     * Get assets information
     *
     * @param assets to retrieve information
     * @return assets information
     * @throws KrakenApiException
     */
    public AssetsInformationResult getAssetsInformation(String... assets) throws KrakenApiException {
        HttpApiClient<AssetsInformationResult> client = (HttpApiClient<AssetsInformationResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);

        Map<String, String> params = new HashMap<>();
        params.put("asset", String.join(",", assets));

        return client.callPublic(BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws KrakenApiException
     */
    public AssetPairsResult getAssetPairs() throws KrakenApiException {
        HttpApiClient<AssetPairsResult> client = (HttpApiClient<AssetPairsResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ASSET_PAIRS);
        return client.callPublic(BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @param info informations to retrieve
     * @param assetPairs asset pairs to retrieve
     * @return asset pairs
     * @throws KrakenApiException
     */
    public AssetPairsResult getAssetPairs(InfoInput info, String... assetPairs) throws KrakenApiException {
        HttpApiClient<AssetPairsResult> client = (HttpApiClient<AssetPairsResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ASSET_PAIRS);

        Map<String, String> params = new HashMap<>();
        params.put("info", info.getValue());
        params.put("pair", String.join(",", assetPairs));

        return client.callPublic(BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params);
    }

    /**
     * Get ticker information of pairs
     *
     * @param pairs list of pair
     * @return ticker information
     * @throws KrakenApiException
     */
    public TickerInformationResult getTickerInformation(List<String> pairs) throws KrakenApiException {
        HttpApiClient<TickerInformationResult> client = (HttpApiClient<TickerInformationResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.TICKER_INFORMATION);

        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return client.callPublic(BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }


    /**
     * Get OHLC data
     *
     * @param pair     currency pair
     * @param interval interval of time
     * @param since    data since given id
     * @return data (OHLC + last id)
     * @throws KrakenApiException
     */
    public OHLCResult getOHLC(String pair, Interval interval, Integer since) throws KrakenApiException {
        HttpApiClient<OHLCResult> client = (HttpApiClient<OHLCResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.OHLC);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));
        params.put("since", String.valueOf(since));

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    /**
     * Get OHLC data
     *
     * @param pair     currency pair
     * @param interval interval of time
     * @return data (OHLC + last id)
     * @throws KrakenApiException
     */
    public OHLCResult getOHLC(String pair, Interval interval) throws KrakenApiException {
        HttpApiClient<OHLCResult> client = (HttpApiClient<OHLCResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.OHLC);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    /**
     * Get order book
     *
     * @param pair  asset pair
     * @param count maximum number of asks/bids
     * @return order book
     * @throws KrakenApiException
     */
    public OrderBookResult getOrderBook(String pair, Integer count) throws KrakenApiException {
        HttpApiClient<OrderBookResult> client = (HttpApiClient<OrderBookResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ORDER_BOOK);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("count", String.valueOf(count));

        return client.callPublic(BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    /**
     * Get order book
     *
     * @param pair asset pair
     * @return order book
     * @throws KrakenApiException
     */
    public OrderBookResult getOrderBook(String pair) throws KrakenApiException {
        HttpApiClient<OrderBookResult> client = (HttpApiClient<OrderBookResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.ORDER_BOOK);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return client.callPublic(BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    /**
     * Get recent trades
     *
     * @param pair asset pair
     * @return recent trades
     * @throws KrakenApiException
     */
    public RecentTradeResult getRecentTrades(String pair) throws KrakenApiException {
        HttpApiClient<RecentTradeResult> client = (HttpApiClient<RecentTradeResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.RECENT_TRADES);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    /**
     * Get recent trades
     *
     * @param pair  asset pair
     * @param since return trade data since given id
     * @return recent trades
     * @throws KrakenApiException
     */
    public RecentTradeResult getRecentTrades(String pair, Integer since) throws KrakenApiException {
        HttpApiClient<RecentTradeResult> client = (HttpApiClient<RecentTradeResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.RECENT_TRADES);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    /**
     * Get recent spreads
     *
     * @param pair asset pair
     * @return recent spreads
     * @throws KrakenApiException
     */
    public RecentSpreadResult getRecentSpreads(String pair) throws KrakenApiException {
        HttpApiClient<RecentSpreadResult> client = (HttpApiClient<RecentSpreadResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.RECENT_SPREADS);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    /**
     * Get recent spreads
     *
     * @param pair  asset pair
     * @param since return spreads since given id
     * @return recent spreads
     * @throws KrakenApiException
     */
    public RecentSpreadResult getRecentSpreads(String pair, Integer since) throws KrakenApiException {
        HttpApiClient<RecentSpreadResult> client = (HttpApiClient<RecentSpreadResult>) this.clientFactory.getHttpApiClient(KrakenApiMethod.RECENT_SPREADS);

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return client.callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    /**
     * Get account balance
     *
     * @return map of pair/balance
     * @throws KrakenApiException
     */
    public AccountBalanceResult getAccountBalance() throws KrakenApiException {
        HttpApiClient<AccountBalanceResult> client = (HttpApiClient<AccountBalanceResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.ACCOUNT_BALANCE);
        return client.callPrivate(BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    /**
     * Get tradable balance
     *
     * @return trade balance
     * @throws KrakenApiException
     */
    public TradeBalanceResult getTradeBalance() throws KrakenApiException {
        HttpApiClient<TradeBalanceResult> client = (HttpApiClient<TradeBalanceResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.TRADE_BALANCE);
        return client.callPrivate(BASE_URL, KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    /**
     * Get open orders
     *
     * @return open orders
     * @throws KrakenApiException
     */
    public OpenOrdersResult getOpenOrders() throws KrakenApiException {
        HttpApiClient<OpenOrdersResult> client = (HttpApiClient<OpenOrdersResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.OPEN_ORDERS);
        return client.callPrivate(BASE_URL, KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    /**
     * Get closed orders
     *
     * @return closed orders
     * @throws KrakenApiException
     */
    public ClosedOrdersResult getClosedOrders() throws KrakenApiException {
        HttpApiClient<ClosedOrdersResult> client = (HttpApiClient<ClosedOrdersResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.CLOSED_ORDERS);
        return client.callPrivate(BASE_URL, KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    /**
     * Get orders information
     *
     * @param transactions list of transactions
     * @return orders information
     * @throws KrakenApiException
     */
    public OrdersInformationResult getOrdersInformation(List<String> transactions) throws KrakenApiException {
        HttpApiClient<OrdersInformationResult> client = (HttpApiClient<OrdersInformationResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.ORDERS_INFORMATION);

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return client.callPrivate(BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    /**
     * Get trades history
     *
     * @return trades history
     * @throws KrakenApiException
     */
    public TradesHistoryResult getTradesHistory() throws KrakenApiException {
        HttpApiClient<TradesHistoryResult> client = (HttpApiClient<TradesHistoryResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.TRADES_HISTORY);
        return client.callPrivate(BASE_URL, KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class);
    }

    /**
     * Get trades information
     *
     * @param transactions list of transactions
     * @return trades information
     * @throws KrakenApiException
     */
    public TradesInformationResult getTradesInformation(List<String> transactions) throws KrakenApiException {
        HttpApiClient<TradesInformationResult> client = (HttpApiClient<TradesInformationResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.TRADES_INFORMATION);

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return client.callPrivate(BASE_URL, KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params);
    }

    /**
     * Get open positions
     *
     * @param transactions list of transactions
     * @return open positions
     * @throws KrakenApiException
     */
    public OpenPositionsResult getOpenPositions(List<String> transactions) throws KrakenApiException {
        HttpApiClient<OpenPositionsResult> client = (HttpApiClient<OpenPositionsResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.OPEN_POSITIONS);

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return client.callPrivate(BASE_URL, KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params);
    }

    /**
     * Get ledgers information
     *
     * @return ledgers information
     * @throws KrakenApiException
     */
    public LedgersInformationResult getLedgersInformation() throws KrakenApiException {
        HttpApiClient<LedgersInformationResult> client = (HttpApiClient<LedgersInformationResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.LEDGERS_INFORMATION);
        return client.callPrivate(BASE_URL, KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class);
    }

    /**
     * Get ledgers
     *
     * @param ledgerIds list of ledger ids
     * @return ledgers
     * @throws KrakenApiException
     */
    public LedgersResult getLedgers(List<String> ledgerIds) throws KrakenApiException {
        HttpApiClient<LedgersResult> client = (HttpApiClient<LedgersResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.QUERY_LEDGERS);

        Map<String, String> params = new HashMap<>();
        params.put("id", ledgerIds.stream().collect(Collectors.joining(",")));

        return client.callPrivate(BASE_URL, KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params);
    }

    /**
     * Get trade volume
     *
     * @return trade volume
     * @throws KrakenApiException
     */
    public TradeVolumeResult getTradeVolume() throws KrakenApiException {
        HttpApiClient<TradeVolumeResult> client = (HttpApiClient<TradeVolumeResult>) this.clientFactory.getHttpApiClient(apiKey, apiSecret, KrakenApiMethod.TRADE_VOLUME);
        return client.callPrivate(BASE_URL, KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class);
    }
}
