package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * AssetPairsResult test
 *
 * @author Stéphane Bouclier
 */
public class AssetPairsResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs.mock.json");
        AssetPairsResult mockResult = new ObjectMapper().readValue(jsonResult, AssetPairsResult.class);

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("AssetPairsResult"));
    }

    @Test
    public void should_test_if_fees_are_equals() {

        AssetPairsResult.AssetPair.Fee fee1 = new AssetPairsResult.AssetPair.Fee(1, 1.0f);
        AssetPairsResult.AssetPair.Fee fee2 = new AssetPairsResult.AssetPair.Fee(1, 2.0f);
        AssetPairsResult.AssetPair.Fee fee3 = new AssetPairsResult.AssetPair.Fee(1, 1.0f);

        assertFalse(fee1.equals(null));
        assertFalse(fee1.equals(new Object()));

        assertFalse(fee1.equals(fee2));
        assertTrue(fee2.equals(fee2));
        assertFalse(fee2.equals(fee3));
        assertTrue(fee1.equals(fee3));
    }
}
