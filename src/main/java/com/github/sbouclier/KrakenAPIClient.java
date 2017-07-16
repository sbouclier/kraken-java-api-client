package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kraken API client
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClient {

    public static String BASE_URL = "https://api.kraken.com/0";

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

    /**
     * Get server time
     *
     * @return server time
     * @throws KrakenApiException
     */
    public ServerTimeResult getServerTime() throws KrakenApiException {
        return new HttpApiClient<ServerTimeResult>()
                .callHttpClient(BASE_URL + "/public/Time", ServerTimeResult.class);
    }

    /**
     * Get asset information
     *
     * @return asset information
     * @throws KrakenApiException
     */
    public AssetInformationResult getAssetInformation() throws KrakenApiException {
        return new HttpApiClient<AssetInformationResult>()
                .callHttpClient(BASE_URL + "/public/Assets", AssetInformationResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws KrakenApiException
     */
    public AssetPairsResult getAssetPairs() throws KrakenApiException {
        return new HttpApiClient<AssetPairsResult>()
                .callHttpClient(BASE_URL + "/public/AssetPairs", AssetPairsResult.class);
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
                .callHttpClient(BASE_URL + "/public/Ticker", TickerInformationResult.class, params);
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
                .callHttpClient(BASE_URL + "/public/OHLC", OHLCResult.class, params);
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
                .callHttpClient(BASE_URL + "/public/OHLC", OHLCResult.class, params);
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
                .callHttpClient(BASE_URL + "/public/Depth", OrderBookResult.class, params);
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
                .callHttpClient(BASE_URL + "/public/Depth", OrderBookResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/public/Trades", RecentTradeResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/public/Trades", RecentTradeResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/public/Spread", RecentSpreadResult.class, params);
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
                .callHttpClientWithLastId(BASE_URL + "/public/Spread", RecentSpreadResult.class, params);
    }

    public static void main(String[] args) throws KrakenApiException, IOException {
        KrakenAPIClient client = new KrakenAPIClient();
        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult resultAssertInfo = client.getAssetInformation();
        //System.out.println("resultAssertInfo:"+resultAssertInfo.getResult());

        //AssetPairsResult result = client.getAssetPairs();
        //TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        //OHLCResult resultOHLC = client.getOHLC("XXBTZEUR", Interval.ONE_DAY);
        //System.out.println("resultOHLC:"+resultOHLC.getResult());


        RecentSpreadResult result = null;
        try {
            result = client.getRecentSpreads("XXBTZEUR", 1500202358);

            System.out.println("getResult():" + result.getResult());
            System.out.println("last id:" + result.getLastId());
        } catch (KrakenApiException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


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
