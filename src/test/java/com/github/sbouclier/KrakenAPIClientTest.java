package com.github.sbouclier;

import com.github.sbouclier.result.AssetInformationResult;
import com.github.sbouclier.result.ServerTimeResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * KrakenAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClientTest {

    private KrakenAPIClient client;
    private CloseableHttpClient mockHttpClient;
    private CloseableHttpResponse mockHttpResponse;
    private HttpEntity mockEntity;

    @Before
    public void setUp() {
        mockHttpClient = mock(CloseableHttpClient.class);
        mockHttpResponse = mock(CloseableHttpResponse.class);
        mockEntity = mock(HttpEntity.class);

        client = new KrakenAPIClient();
        client.setClient(mockHttpClient);
    }

    @Test
    public void testGetServerTime() throws IOException {
        String mockResponseBody = "{\"error\":[],\"result\":{\"unixtime\":1498768391,\"rfc1123\":\"Thu, 29 Jun 17 20:33:11 +0000\"}}";

        when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"));
        when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.getBytes("UTF-8")));

        ServerTimeResult result = client.getServerTime();
        assertThat(result.getResult(), hasProperty("unixtime", equalTo(1498768391L)));
        assertThat(result.getResult(), hasProperty("rfc1123", equalTo("Thu, 29 Jun 17 20:33:11 +0000")));
    }

    @Test
    public void testGetAssetInformation() throws IOException {
        StringBuilder mockResponseBody = new StringBuilder("{\"error\":[],\"result\":{");
        mockResponseBody.append("\"XETC\":{\"aclass\":\"currency\",\"altname\":\"ETC\",\"decimals\":10,\"display_decimals\":5},");
        mockResponseBody.append("\"XETH\":{\"aclass\":\"currency\",\"altname\":\"ETH\",\"decimals\":10,\"display_decimals\":5},");
        mockResponseBody.append("\"ZEUR\":{\"aclass\":\"currency\",\"altname\":\"EUR\",\"decimals\":4,\"display_decimals\":2},");
        mockResponseBody.append("\"ZUSD\":{\"aclass\":\"currency\",\"altname\":\"USD\",\"decimals\":4,\"display_decimals\":2}");
        mockResponseBody.append("}}");

        AssetInformationResult.AssetInformation xetc = new AssetInformationResult.AssetInformation();
        xetc.setAlternateName("ETC");
        xetc.setAssetClass("currency");
        xetc.setDecimals((byte) 10);
        xetc.setDisplayDecimals((byte) 5);

        AssetInformationResult.AssetInformation xeth = new AssetInformationResult.AssetInformation();
        xeth.setAlternateName("ETH");
        xeth.setAssetClass("currency");
        xeth.setDecimals((byte) 10);
        xeth.setDisplayDecimals((byte) 5);

        AssetInformationResult.AssetInformation zeur = new AssetInformationResult.AssetInformation();
        zeur.setAlternateName("EUR");
        zeur.setAssetClass("currency");
        zeur.setDecimals((byte) 4);
        zeur.setDisplayDecimals((byte) 2);

        AssetInformationResult.AssetInformation zusd = new AssetInformationResult.AssetInformation();
        zusd.setAlternateName("USD");
        zusd.setAssetClass("currency");
        zusd.setDecimals((byte) 4);
        zusd.setDisplayDecimals((byte) 2);

        when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"));
        when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.toString().getBytes("UTF-8")));

        AssetInformationResult result = client.getAssetInformation();

        assertEquals(result.getResult().size(), 4);
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));
    }
}
