package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * TradeBalanceResult test
 *
 * @author Stéphane Bouclier
 */
public class TradeBalanceResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/trade_balance.mock.json");
        TradeBalanceResult mockResult = new ObjectMapper().readValue(jsonResult, TradeBalanceResult.class);

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("TradeBalanceResult"));
    }
}
