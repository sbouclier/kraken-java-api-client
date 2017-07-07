package com.github.sbouclier;

import com.github.sbouclier.result.AssetInformationResult;
import com.github.sbouclier.result.AssetPairsResult;
import com.github.sbouclier.result.ServerTimeResult;
import com.github.sbouclier.result.TickerInformationResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kraken API client
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClient {

    /**
     * Get server time
     *
     * @return server time
     * @throws IOException
     */
    public ServerTimeResult getServerTime() throws IOException {
        return new HttpApiClient<ServerTimeResult>()
                .callHttpClient("https://api.kraken.com/0/public/Time", ServerTimeResult.class);
    }

    /**
     * Get asset information
     *
     * @return asset information
     * @throws IOException
     */
    public AssetInformationResult getAssetInformation() throws IOException {
        return new HttpApiClient<AssetInformationResult>()
                .callHttpClient("https://api.kraken.com/0/public/Assets", AssetInformationResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws IOException
     */
    public AssetPairsResult getAssetPairs() throws IOException {
        return new HttpApiClient<AssetPairsResult>()
                .callHttpClient("https://api.kraken.com/0/public/AssetPairs", AssetPairsResult.class);
    }

    /**
     * Get ticker information of pairs
     *
     * @param pairs list of pair
     * @return ticker information
     * @throws IOException
     */
    public TickerInformationResult getTickerInformation(List<String> pairs) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return new HttpApiClient<TickerInformationResult>()
                .callHttpClient("https://api.kraken.com/0/public/Ticker", TickerInformationResult.class, params);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        KrakenAPIClient client = new KrakenAPIClient();
        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult result = client.getAssetInformation();
        //AssetPairsResult result = client.getAssetPairs();
        TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR","ETHEUR"));

        System.out.println(result.getResult());
        System.out.println(result.getResult().get("XETHZEUR").ask + " > " + result.getResult().get("XETHZEUR").ask.getClass());
    }
}
