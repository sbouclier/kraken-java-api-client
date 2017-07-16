package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.result.Result;
import com.github.sbouclier.result.ResultWithLastId;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        params.forEach((k, v) -> builder.addParameter(k, v));

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

    /**
     * Make a http call, extract last id from response and return unmarshalled result
     *
     * @param url    url to call
     * @param result class of result
     * @param params request parameters
     * @return call result
     * @throws IOException
     */
    public T callHttpClientWithLastId(String url, Class<T> result, Map<String, String> params) throws KrakenApiException {
        HttpGet httpGet = new HttpGet(url);
        URIBuilder builder = new URIBuilder(httpGet.getURI());
        params.forEach((k, v) -> builder.addParameter(k, v));

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
        httpGet.setURI(uri);

        T res = executeQueryWithLastId(httpGet, result);

        return res;
    }

    /**
     * Execute http get query and unmarshall result
     *
     * @param httpGet http get
     * @param result  class result
     * @return unmarshalled result
     * @throws KrakenApiException
     */
    private T executeQuery(HttpGet httpGet, Class<T> result) throws KrakenApiException {
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            System.out.println("status " + response.getStatusLine().getStatusCode());
            System.out.println(responseString);
            T res = new ObjectMapper().readValue(responseString, result);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            httpClient.close();

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * Execute http get query, extract last id and unmarshall result
     *
     * @param httpGet http get
     * @param result  class result
     * @return unmarshalled result
     * @throws KrakenApiException
     */
    private T executeQueryWithLastId(HttpGet httpGet, Class<T> result) throws KrakenApiException {
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            System.out.println("status " + response.getStatusLine().getStatusCode());
            System.out.println(responseString);

            LastIdExtractedResult extractedResult = extractLastId(responseString);

            T res = new ObjectMapper().readValue(extractedResult.responseWithoutLastId, result);
            ((ResultWithLastId) res).setLastId(extractedResult.lastId);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            httpClient.close();

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * LastId extracted class result
     */
    private class LastIdExtractedResult {
        public String responseWithoutLastId;
        public Long lastId;

        public LastIdExtractedResult(String responseWithoutLastId, Long lastId) {
            this.responseWithoutLastId = responseWithoutLastId;
            this.lastId = lastId;
        }
    }

    /**
     * Extract last id from string and wrap it with response without it
     *
     * @param response
     * @return extracted result
     * @throws KrakenApiException
     */
    private LastIdExtractedResult extractLastId(String response) throws KrakenApiException {
        Pattern pattern = Pattern.compile(",\"last\":\"([0-9]+)\"");
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            response = response.replaceAll(",\"last\":\"([0-9]+)\"", "");
            return new LastIdExtractedResult(response, Long.valueOf(matcher.group(1)));
        } else {
            throw new KrakenApiException("unuble to extract last id");
        }
    }
}
