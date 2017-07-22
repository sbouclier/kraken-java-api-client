package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .callHttpClient(BASE_URL + "/0/public/Time", ServerTimeResult.class);
    }

    /**
     * Get asset information
     *
     * @return asset information
     * @throws KrakenApiException
     */
    public AssetInformationResult getAssetInformation() throws KrakenApiException {
        return new HttpApiClient<AssetInformationResult>()
                .callHttpClient(BASE_URL + "/0/public/Assets", AssetInformationResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws KrakenApiException
     */
    public AssetPairsResult getAssetPairs() throws KrakenApiException {
        return new HttpApiClient<AssetPairsResult>()
                .callHttpClient(BASE_URL + "/0/public/AssetPairs", AssetPairsResult.class);
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
                .callHttpClient(BASE_URL + "/0/public/Ticker", TickerInformationResult.class, params);
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
                .callHttpClient(BASE_URL + "/0/public/OHLC", OHLCResult.class, params);
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
                .callHttpClient(BASE_URL + "/0/public/OHLC", OHLCResult.class, params);
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
                .callHttpClient(BASE_URL + "/0/public/Depth", OrderBookResult.class, params);
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
                .callHttpClient(BASE_URL + "/0/public/Depth", OrderBookResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/0/public/Trades", RecentTradeResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/0/public/Trades", RecentTradeResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/0/public/Spread", RecentSpreadResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/0/public/Spread", RecentSpreadResult.class, params);
    }

    /**
     * Get account balance
     *
     * @return map of pair/balance
     * @throws KrakenApiException
     */
    public AccountBalanceResult getAccountBalance() throws KrakenApiException {
        return new HttpApiClient<AccountBalanceResult>(this.apiKey, this.apiSecret)
                .callSecuredHttpClient(BASE_URL, "/0/private/Balance", AccountBalanceResult.class);
    }

    /**
     * Get tradable balance
     *
     * @return trade balance
     * @throws KrakenApiException
     */
    public TradeBalanceResult getTradeBalance() throws KrakenApiException {
        return new HttpApiClient<TradeBalanceResult>(this.apiKey, this.apiSecret)
                .callSecuredHttpClient(BASE_URL, "/0/private/TradeBalance", TradeBalanceResult.class);
    }

    /**
     * Get open orders
     *
     * @return open orders
     * @throws KrakenApiException
     */
    public OpenOrdersResult getOpenOrdersResult() throws KrakenApiException {
        return new HttpApiClient<OpenOrdersResult>(this.apiKey, this.apiSecret)
                .callSecuredHttpClient(BASE_URL, "/0/private/OpenOrders", OpenOrdersResult.class);
    }

    /**
     * Get closed orders
     *
     * @return closed orders
     * @throws KrakenApiException
     */
    public ClosedOrdersResult getClosedOrdersResult() throws KrakenApiException {
        return new HttpApiClient<ClosedOrdersResult>(this.apiKey, this.apiSecret)
                .callSecuredHttpClient(BASE_URL, "/0/private/ClosedOrders", ClosedOrdersResult.class);
    }

    public static void main(String[] args) throws KrakenApiException {
        KrakenAPIClient client = new KrakenAPIClient("","");



        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult resultAssertInfo = client.getAssetInformation();
        //System.out.println("resultAssertInfo:"+resultAssertInfo.getResult());

        //AssetPairsResult result = client.getAssetPairs();
        //TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        //OHLCResult resultOHLC = client.getOHLC("XXBTZEUR", Interval.ONE_DAY);
        //System.out.println("resultOHLC:"+resultOHLC.getResult());

        //AccountBalanceResult accountBalanceResult = client.getAccountBalance();
        //accountBalanceResult.getResult().forEach((currency, balance) -> System.out.println(currency + " = " + balance));
        //System.out.println(accountBalanceResult.getResult());

        //TradeBalanceResult tradeBalanceResult = client.getTradeBalance();
        //System.out.println(tradeBalanceResult.getResult());

        //OpenOrdersResult openOrders = client.getOpenOrdersResult();
        //System.out.println(openOrders.getResult());

        ClosedOrdersResult closedOrders = client.getClosedOrdersResult();
        System.out.println(closedOrders.getResult());

        // OK
        //String responseString = "{\"error\":[],\"result\":{\"XXBTZEUR\":[[\"1751.70000\",\"0.12213919\",1500127273.3728,\"s\",\"l\",\"\"],[\"1751.44100\",\"0.72700000\",1500127273.4011,\"s\",\"l\",\"\"]]}}";
        //Object res = new ObjectMapper().readValue(responseString, RecentTradeResult.class);
        //System.out.println("res>"+res);

        String responseString2 = "{\"error\":[],\"result\":{\"XXBTZEUR\":[[1500197914,\"1671.00000\",\"1671.00000\"],[1500197914,\"1670.29400\",\"1671.00000\"],[1500197921,\"1671.00000\",\"1671.00000\"]],\"last\":1499990400}}";


        //String replace = responseString2.replaceAll(",\"last\":([0-9]+)", "");
        //System.out.println("replace:"+replace);

        //Object res2 = new ObjectMapper().readValue(replace, RecentTradeResult.class);
        //System.out.println("res2>"+res2);
    }
}
