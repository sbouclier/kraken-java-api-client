package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.result.Result;
import com.github.sbouclier.result.ResultWithLastId;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Http API client
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClient<T extends Result> {

    private HttpJsonClient client;
    private int apiVersion = 0;

    public HttpApiClient() {
        client = new HttpJsonClient();
    }

    public HttpApiClient(String apiKey, String secret) {
        client = new HttpJsonClient(apiKey, secret);
    }

    public HttpApiClient(HttpJsonClient client) {
        this.client = client;
    }

    /**
     * Call public kraken method
     *
     * @param baseUrl kraken base url
     * @param method  kraken method
     * @param result  result class
     * @return result
     * @throws KrakenApiException
     */
    public T callPublic(String baseUrl, KrakenApiMethod method, Class<T> result) throws KrakenApiException {
        try {
            final String responseString = this.client.executePublicQuery(baseUrl, method.getUrl(apiVersion));
            T res = new ObjectMapper().readValue(responseString, result);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * Call public kraken method
     *
     * @param baseUrl kraken base url
     * @param method  kraken method
     * @param result  result class
     * @param params  method parameters
     * @return result
     * @throws KrakenApiException
     */
    public T callPublic(String baseUrl, KrakenApiMethod method, Class<T> result, Map<String, String> params) throws KrakenApiException {
        try {
            final String responseString = this.client.executePublicQuery(baseUrl, method.getUrl(apiVersion), params);
            T res = new ObjectMapper().readValue(responseString, result);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * Call public kraken method and extract last id
     *
     * @param baseUrl kraken base url
     * @param method  kraken method
     * @param result  result class
     * @param params  method parameters
     * @return result
     * @throws KrakenApiException
     */
    public T callPublicWithLastId(String baseUrl, KrakenApiMethod method, Class<T> result, Map<String, String> params) throws KrakenApiException {
        try {
            final String responseString = this.client.executePublicQuery(baseUrl, method.getUrl(apiVersion), params);

            LastIdExtractedResult extractedResult = extractLastId(responseString);

            T res = new ObjectMapper().readValue(extractedResult.responseWithoutLastId, result);
            ((ResultWithLastId) res).setLastId(extractedResult.lastId);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * Call private kraken method
     *
     * @param baseUrl kraken base url
     * @param method  kraken method
     * @param result  result class
     * @return result
     * @throws KrakenApiException
     */
    public T callPrivate(String baseUrl, KrakenApiMethod method, Class<T> result) throws KrakenApiException {
        try {
            final String responseString = this.client.executePrivateQuery(baseUrl, method.getUrl(apiVersion));
            T res = new ObjectMapper().readValue(responseString, result);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

            return res;
        } catch (IOException ex) {
            throw new KrakenApiException("unable to query Kraken API", ex);
        }
    }

    /**
     * Call private kraken method
     *
     * @param baseUrl kraken base url
     * @param method  kraken method
     * @param result  result class
     * @param params  method parameters
     * @return result
     * @throws KrakenApiException
     */
    public T callPrivate(String baseUrl, KrakenApiMethod method, Class<T> result, Map<String, String> params) throws KrakenApiException {
        try {
            final String responseString = this.client.executePrivateQuery(baseUrl, method.getUrl(apiVersion), params);
            T res = new ObjectMapper().readValue(responseString, result);

            if (!res.getError().isEmpty()) {
                throw new KrakenApiException(res.getError());
            }

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
        final String lastPattern = ",(\\s*)\"last\":\"{0,1}([0-9]+)\"{0,1}";

        Pattern pattern = Pattern.compile(lastPattern);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            response = response.replaceAll(lastPattern, "");

            return new LastIdExtractedResult(response, Long.valueOf(matcher.group(2)));
        } else {
            throw new KrakenApiException("unable to extract last id");
        }
    }
}
