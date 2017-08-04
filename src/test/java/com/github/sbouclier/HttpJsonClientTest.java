package com.github.sbouclier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * HttpJsonClient test
 *
 * @author Stéphane Bouclier
 */
public class HttpJsonClientTest {

    @Before
    public void setUp() throws IOException {
        //spyClient = Mockito.spy(HttpJsonClient.class);
    }

    @After
    public void tearDown() throws Exception {
        //verifyNoMoreInteractions(spyClient);
    }

    @Test
    public void should_execute_public_query_without_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient spyClient = Mockito.spy(HttpJsonClient.class);
        Mockito.doReturn("response").when(spyClient).getPublicJsonResponse(new URL("https://baseUrl/urlMethod?"));

        // When
        String result = spyClient.executePublicQuery("https://baseUrl", "/urlMethod");

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_execute_public_query_with_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient spyClient = Mockito.spy(HttpJsonClient.class);

        Map<String, String> params = new HashMap<>();
        params.put("a", "A");
        params.put("b", "B");
        params.put("c", "C");

        Mockito.doReturn("response").when(spyClient).getPublicJsonResponse(new URL("https://baseUrl/urlMethod?a=A&b=B&c=C&"));

        // When
        String result = spyClient.executePublicQuery("https://baseUrl", "/urlMethod", params);

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_execute_public_query_with_empty_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient spyClient = Mockito.spy(HttpJsonClient.class);
        Mockito.doReturn("response").when(spyClient).getPublicJsonResponse(new URL("https://baseUrl/urlMethod?"));

        // When
        String result = spyClient.executePublicQuery("https://baseUrl", "/urlMethod", new HashMap<>());

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_retrieve_public_json_response() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient spyClient = Mockito.spy(HttpJsonClient.class);
        Mockito.doReturn("response").when(spyClient).getJsonResponse(any());

        // When
        String result = spyClient.getPublicJsonResponse(new URL("https://baseUrl"));

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_execute_private_query_without_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient client = new HttpJsonClient("apiKey", "secret");
        HttpJsonClient spyClient = Mockito.spy(client);

        final String postData = "nonce=123456";
        final String signature = "pmK1HtzTC8XQxUYJRgZ+Ae5aLdvH1cx6eJssELGVlWbt+pbFE96CYUzAujeqeDPijYTOEN5b/vrreWnURZij+w==";

        Mockito.doReturn("123456").when(spyClient).generateNonce();
        Mockito.doReturn("response").when(spyClient).getPrivateJsonResponse(new URL("https://baseUrl/urlMethod"), postData, signature);

        // When
        String result = spyClient.executePrivateQuery("https://baseUrl", "/urlMethod");

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_execute_private_query_with_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient client = new HttpJsonClient("apiKey", "secret");
        HttpJsonClient spyClient = Mockito.spy(client);

        Map<String, String> params = new HashMap<>();
        params.put("a", "A");
        params.put("b", "B");
        params.put("c", "C");

        final String postData = "a=A&b=B&c=C&nonce=123456";
        final String signature = "roSiYVygbc6rEyZHxmAx0tv5aftiZsm2v97g2n5tttsEleJ3wsShHK8Y4ewjCPs8YgaHsGMAwmwT/9aHC02UYQ==";

        Mockito.doReturn("123456").when(spyClient).generateNonce();
        Mockito.doReturn("response").when(spyClient).getPrivateJsonResponse(new URL("https://baseUrl/urlMethod"), postData, signature);

        // When
        String result = spyClient.executePrivateQuery("https://baseUrl", "/urlMethod", params);

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test
    public void should_execute_private_query_with_empty_params() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient client = new HttpJsonClient("apiKey", "secret");
        HttpJsonClient spyClient = Mockito.spy(client);

        final String postData = "nonce=123456";
        final String signature = "pmK1HtzTC8XQxUYJRgZ+Ae5aLdvH1cx6eJssELGVlWbt+pbFE96CYUzAujeqeDPijYTOEN5b/vrreWnURZij+w==";

        Mockito.doReturn("123456").when(spyClient).generateNonce();
        Mockito.doReturn("response").when(spyClient).getPrivateJsonResponse(new URL("https://baseUrl/urlMethod"), postData, signature);

        // When
        String result = spyClient.executePrivateQuery("https://baseUrl", "/urlMethod", new HashMap<>());

        // Then
        assertThat(result, equalTo("response"));
    }

    @Test(expected = KrakenApiException.class)
    public void should_throw_exception_if_no_api_key_provided_for_private_query() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient client = new HttpJsonClient(null, "secret");

        // When
        String result = client.executePrivateQuery("https://baseUrl", "/urlMethod");

        // Then exception
    }

    @Test(expected = KrakenApiException.class)
    public void should_throw_exception_if_no_secret_provided_for_private_query() throws IOException, KrakenApiException {

        // Given
        HttpJsonClient client = new HttpJsonClient("key", null);

        // When
        String result = client.executePrivateQuery("https://baseUrl", "/urlMethod");

        // Then exception
    }
}
