package com.github.sbouclier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.result.AssetInformationResult;
import com.github.sbouclier.result.ServerTimeResult;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * KrakenAPIClient test
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClientTest {

    private HttpApiClientFactory mockClientFactory;
    private HttpApiClient mockClient;

    @Before
    public void setUp() throws IOException {
        this.mockClientFactory = mock(HttpApiClientFactory.class);
        this.mockClient = mock(HttpApiClient.class);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mockClientFactory, mockClient);
    }

    @Test
    public void should_return_server_time() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/server_time.mock.json");
        ServerTimeResult mockResult = new ObjectMapper().readValue(jsonResult, ServerTimeResult.class);

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.SERVER_TIME)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        ServerTimeResult result = client.getServerTime();
        ServerTimeResult.ServerTime serverTime = result.getResult();

        // Then
        assertThat(serverTime.unixtime, equalTo(1501271914L));
        assertThat(serverTime.rfc1123, equalTo("Fri, 28 Jul 17 19:58:34 +0000"));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.SERVER_TIME);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    @Test
    public void should_return_asset_information() throws KrakenApiException, IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_information.mock.json");
        AssetInformationResult mockResult = new ObjectMapper().readValue(jsonResult, AssetInformationResult.class);

        AssetInformationResult.AssetInformation xetc = new AssetInformationResult.AssetInformation();
        xetc.alternateName = "ETC";
        xetc.assetClass = "currency";
        xetc.decimals = (byte) 10;
        xetc.displayDecimals = (byte) 5;

        AssetInformationResult.AssetInformation xeth = new AssetInformationResult.AssetInformation();
        xeth.alternateName = "ETH";
        xeth.assetClass = "currency";
        xeth.decimals = (byte) 10;
        xeth.displayDecimals = (byte) 5;

        AssetInformationResult.AssetInformation zeur = new AssetInformationResult.AssetInformation();
        zeur.alternateName = "EUR";
        zeur.assetClass = "currency";
        zeur.decimals = (byte) 4;
        zeur.displayDecimals = (byte) 2;

        AssetInformationResult.AssetInformation zusd = new AssetInformationResult.AssetInformation();
        zusd.alternateName = "USD";
        zusd.assetClass = "currency";
        zusd.decimals = (byte) 4;
        zusd.displayDecimals = (byte) 2;

        // When
        when(mockClientFactory.getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION)).thenReturn(mockClient);
        when(mockClient.callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class)).thenReturn(mockResult);

        KrakenAPIClient client = new KrakenAPIClient(mockClientFactory);
        AssetInformationResult result = client.getAssetInformation();

        // Then
        assertEquals(26, result.getResult().size());
        assertThat(result.getResult().get("XETC"), samePropertyValuesAs(xetc));
        assertThat(result.getResult().get("XETH"), samePropertyValuesAs(xeth));
        assertThat(result.getResult().get("ZEUR"), samePropertyValuesAs(zeur));
        assertThat(result.getResult().get("ZUSD"), samePropertyValuesAs(zusd));

        verify(mockClientFactory).getHttpApiClient(KrakenApiMethod.ASSET_INFORMATION);
        verify(mockClient).callPublic(KrakenAPIClient.BASE_URL, KrakenApiMethod.ASSET_INFORMATION, AssetInformationResult.class);
    }
}
