package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.result.Result;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.apache.http.impl.client.HttpClients.createDefault;

/**
 * Http API client
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClient<T extends Result> {

    private CloseableHttpClient httpClient;

    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    /**
     * Default constructor
     */
    public HttpApiClient() {
        this.httpClient = createDefault();
    }

    /**
     * Constructor with http client injection
     *
     * @param httpClient
     */
    public HttpApiClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // -----------
    // - METHODS -
    // -----------

    /**
     * Make a http call and return unmarshalled result
     *
     * @param url    url to call
     * @param result class of result
     * @return call result
     * @throws IOException
     */
    public T callHttpClient(String url, Class<T> result) throws KrakenApiException {
        HttpGet httpGet = new HttpGet(url);

        T res = executeQuery(httpGet, result);

        return res;
    }

    /**
     * Make a http call and return unmarshalled result
     *
     * @param url    url to call
     * @param result class of result
     * @param params request parameters
     * @return call result
     * @throws IOException
     */
    public T callHttpClient(String url, Class<T> result, Map<String, String> params) throws KrakenApiException {
        HttpGet httpGet = new HttpGet(url);
        URIBuilder builder = new URIBuilder(httpGet.getURI());
        params.forEach((k,v) -> builder.addParameter(k, v));

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
        httpGet.setURI(uri);

        T res = executeQuery(httpGet, result);

        return res;
    }

    private T executeQuery(HttpGet httpGet, Class<T> result) throws KrakenApiException {
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            System.out.println("status " + response.getStatusLine().getStatusCode());
            System.out.println(responseString);
            T res = new ObjectMapper().readValue(responseString, result);

            if(!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            httpClient.close();

            return res;
        } catch(IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }
}
