package com.github.sbouclier;

import com.github.sbouclier.result.ServerTimeResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * KrakenAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClientTest {

    static KrakenAPIClient client;

    @BeforeClass
    public static void setUp() {
        client = new KrakenAPIClient();
    }

    @Test
    public void testGetServerTime() throws IOException {
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        String mockResponseBody = "{\"error\":[],\"result\":{\"unixtime\":1498768391,\"rfc1123\":\"Thu, 29 Jun 17 20:33:11 +0000\"}}";

        when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"));
        when(mockHttpResponse.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.getBytes("UTF-8")));

        client.setClient(mockHttpClient);

        ServerTimeResult result = client.getServerTime();

        Assert.assertEquals(result.getResult().getRfc1123(), "Thu, 29 Jun 17 20:33:11 +0000");
        Assert.assertEquals(result.getResult().getUnixtime(), Long.valueOf(1498768391L));
    }
}
