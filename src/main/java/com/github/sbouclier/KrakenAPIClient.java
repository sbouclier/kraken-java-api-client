package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.util.Arrays;
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

    private String apiKey;
    private String apiSecret;

    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    /**
     * Default constructor to call public API requests
     */
    public KrakenAPIClient() {
    }

    /**
     * Secure constructor to call private API requests
     *
     * @param apiKey
     * @param apiSecret
     */
    public KrakenAPIClient(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    // ---------------
    // - INNER ENUMS -
    // ---------------

    public enum Interval {

        ONE_MINUTE(1),
        FIVE_MINUTES(5),
        FIFTEEN_MINUTES(15),
        THIRTY_MINUTES(30),
        ONE_HOUR(60),
        FOUR_HOURS(240),
        ONE_DAY(1440),
        ONE_WEEK(10080),
        FIFTEEN_DAYS(21600);

        private int minutes;

        Interval(int minutes) {
            this.minutes = minutes;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "minutes=" + minutes +
                    '}';
        }
    }

    // -----------
    // - METHPDS -
    // -----------

    /**
     * Get server time
     *
     * @return server time
     * @throws KrakenApiException
     */
    public ServerTimeResult getServerTime() throws KrakenApiException {
        return new HttpApiClient<ServerTimeResult>()
                .callPublic(BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    /**
     * Get asset information
     *
     * @return asset information
     * @throws KrakenApiException
     */
    public AssetInformationResult getAssetInformation() throws KrakenApiException {
        return new HttpApiClient<AssetInformationResult>()
                .callPublic(BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws KrakenApiException
     */
    public AssetPairsResult getAssetPairs() throws KrakenApiException {
        return new HttpApiClient<AssetPairsResult>()
                .callPublic(BASE_URL, KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    /**
     * Get ticker information of pairs
     *
     * @param pairs list of pair
     * @return ticker information
     * @throws KrakenApiException
     */
    public TickerInformationResult getTickerInformation(List<String> pairs) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return new HttpApiClient<TickerInformationResult>()
                .callPublic(BASE_URL, KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }


    /**
     * Get OHLC data
     *
     * @param pair     currency pair
     * @param interval interval of time
     * @param since    data since givene id
     * @return data (OHLC + last id)
     * @throws KrakenApiException
     */
    public OHLCResult getOHLC(String pair, Interval interval, Integer since) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.minutes));
        params.put("since", String.valueOf(since));

        return new HttpApiClient<OHLCResult>()
                .callPublic(BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
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
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.minutes));

        return new HttpApiClient<OHLCResult>()
                .callPublic(BASE_URL, KrakenApiMethod.OHLC, OHLCResult.class, params);
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
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("count", String.valueOf(count));

        return new HttpApiClient<OrderBookResult>()
                .callPublic(BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    /**
     * Get order book
     *
     * @param pair asset pair
     * @return order book
     * @throws KrakenApiException
     */
    public OrderBookResult getOrderBook(String pair) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return new HttpApiClient<OrderBookResult>()
                .callPublic(BASE_URL, KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    /**
     * Get recent trades
     *
     * @param pair asset pair
     * @return recent trades
     * @throws KrakenApiException
     */
    public RecentTradeResult getRecentTrades(String pair) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return new HttpApiClient<RecentTradeResult>()
                .callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
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
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return new HttpApiClient<RecentTradeResult>()
                .callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    /**
     * Get recent spreads
     *
     * @param pair asset pair
     * @return recent spreads
     * @throws KrakenApiException
     */
    public RecentSpreadResult getRecentSpreads(String pair) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return new HttpApiClient<RecentSpreadResult>()
                .callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
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
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return new HttpApiClient<RecentSpreadResult>()
                .callPublicWithLastId(BASE_URL, KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    /**
     * Get account balance
     *
     * @return map of pair/balance
     * @throws KrakenApiException
     */
    public AccountBalanceResult getAccountBalance() throws KrakenApiException {
        return new HttpApiClient<AccountBalanceResult>(this.apiKey, this.apiSecret)
                .callPrivate(BASE_URL, KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    /**
     * Get tradable balance
     *
     * @return trade balance
     * @throws KrakenApiException
     */
    public TradeBalanceResult getTradeBalance() throws KrakenApiException {
        return new HttpApiClient<TradeBalanceResult>(this.apiKey, this.apiSecret)
                .callPrivate(BASE_URL, KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    /**
     * Get open orders
     *
     * @return open orders
     * @throws KrakenApiException
     */
    public OpenOrdersResult getOpenOrdersResult() throws KrakenApiException {
        return new HttpApiClient<OpenOrdersResult>(this.apiKey, this.apiSecret)
                .callPrivate(BASE_URL, KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    /**
     * Get closed orders
     *
     * @return closed orders
     * @throws KrakenApiException
     */
    public ClosedOrdersResult getClosedOrdersResult() throws KrakenApiException {
        return new HttpApiClient<ClosedOrdersResult>(this.apiKey, this.apiSecret)
                .callPrivate(BASE_URL, KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    /**
     * Get orders information
     *
     * @return orders information
     * @throws KrakenApiException
     */
    public OrdersInformationResult getOrdersInformationResult(List<String> transactions) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return new HttpApiClient<OrdersInformationResult>(this.apiKey, this.apiSecret)
                .callPrivate(BASE_URL, KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    public static void main(String[] args) throws KrakenApiException {
        KrakenAPIClient client = new KrakenAPIClient(
                "",
                "");

        //ServerTimeResult serverTimeResult = client.getServerTime();
        //System.out.println(serverTimeResult);

        //AssetInformationResult resultAssertInfo = client.getAssetInformation();
        //System.out.println("resultAssertInfo:"+resultAssertInfo.getResult());

        //AssetPairsResult assetPairsResult = client.getAssetPairs();
        //System.out.println(assetPairsResult);

        //TickerInformationResult tickerInformationResult = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        //System.out.println(tickerInformationResult);

        //OHLCResult resultOHLC = client.getOHLC("XXBTZEUR", Interval.ONE_DAY);
        //System.out.println("resultOHLC:"+resultOHLC.getResult());

        //OrderBookResult orderBookResult = client.getOrderBook("BTCEUR");
        //System.out.println(orderBookResult);

        RecentTradeResult result = client.getRecentTrades("BTCEUR");
        System.out.println(result.getResult());
        System.out.println("last id: "+result.getLastId());

        RecentSpreadResult recentSpreadResult = client.getRecentSpreads("BTCEUR");
        System.out.println(recentSpreadResult.getResult());
        System.out.println("last id: "+recentSpreadResult.getLastId());

        AccountBalanceResult accountBalanceResult = client.getAccountBalance();
        accountBalanceResult.getResult().forEach((currency, balance) -> System.out.println(currency + " = " + balance));
        System.out.println(accountBalanceResult.getResult());

        TradeBalanceResult tradeBalanceResult = client.getTradeBalance();
        System.out.println(tradeBalanceResult.getResult());

        OpenOrdersResult openOrders = client.getOpenOrdersResult();
        System.out.println(openOrders.getResult());
        System.out.println("trroor");
        ClosedOrdersResult closedOrders = client.getClosedOrdersResult();
        System.out.println(closedOrders.getResult());
        System.out.println("trrr");
        OrdersInformationResult ordersInformationResult = client.getOrdersInformationResult(Arrays.asList("PGRQC4-Q5C5N-2EYZDP"));
        System.out.println(ordersInformationResult.getResult());
        ordersInformationResult.getResult().forEach((txid, order) -> System.out.println(txid + " = " + order.description.type));
    }
}
