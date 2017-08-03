package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * ClosedOrdersResult test
 *
 * @author Stéphane Bouclier
 */
public class ClosedOrdersResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/closed_orders.mock.json");
        ClosedOrdersResult mockResult = new ObjectMapper().readValue(jsonResult, ClosedOrdersResult.class);

        // When
        final String toString = mockResult.toString();
        final String openOrdersToString = mockResult.getResult().toString();
        final String descriptionToString = mockResult.getResult().closed.get("OGRQC4-Q5C5N-2EYZDP").description.toString();

        // Then
        assertThat(toString, startsWith("ClosedOrdersResult"));
        assertThat(openOrdersToString, startsWith("ClosedOrders"));
        assertThat(descriptionToString, startsWith("ClosedOrdersResult.ClosedOrder.Description"));
    }
}
