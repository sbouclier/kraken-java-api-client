package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static void main(String[] args) {
        KrakenAPIClient client = new KrakenAPIClient();
        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult result = client.getAssetInformation();
        //AssetPairsResult result = client.getAssetPairs();
        //TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        OrderBookResult result = null;
        try {
            result = client.getOrderBook("XXBTZEUR", 3);

            // System.out.println(result.getResult().get("XXBTZEUR"));
            System.out.println(result.getResult());
            //System.out.println(result.getResult().get("XETHZEUR").ask + " > " + result.getResult().get("XETHZEUR").ask.getClass());
        } catch (KrakenApiException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
}
