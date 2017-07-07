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
    public T callHttpClient(String url, Class<T> result) throws IOException {
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String responseString = new BasicResponseHandler().handleResponse(response);
        System.out.println(responseString);
        T res = new ObjectMapper().readValue(responseString, result);

        httpClient.close();

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
    public T callHttpClient(String url, Class<T> result, Map<String, String> params) throws IOException, URISyntaxException {
        HttpGet httpGet = new HttpGet(url);
        URIBuilder builder = new URIBuilder(httpGet.getURI());
        params.forEach((k,v) -> builder.addParameter(k, v));

        URI uri = builder.build();
        httpGet.setURI(uri);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String responseString = new BasicResponseHandler().handleResponse(response);
        System.out.println(responseString);
        T res = new ObjectMapper().readValue(responseString, result);

        httpClient.close();

        return res;
    }
}
