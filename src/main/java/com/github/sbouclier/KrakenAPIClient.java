package com.github.sbouclier;

import com.github.sbouclier.result.AssetInformationResult;
import com.github.sbouclier.result.AssetPairsResult;
import com.github.sbouclier.result.ServerTimeResult;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

import static org.apache.http.impl.client.HttpClients.createDefault;

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

    public static void main(String[] args) throws IOException {
        KrakenAPIClient client = new KrakenAPIClient();
        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult result = client.getAssetInformation();
        AssetPairsResult result = client.getAssetPairs();

        System.out.println(result.getResult());
    }
}
