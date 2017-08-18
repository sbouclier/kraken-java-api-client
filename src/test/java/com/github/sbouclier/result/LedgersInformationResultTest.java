package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * LedgersInformationResult test
 *
 * @author Stéphane Bouclier
 */
public class LedgersInformationResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/ledgers_information.mock.json");
        LedgersInformationResult mockResult = new ObjectMapper().readValue(jsonResult, LedgersInformationResult.class);

        System.out.println(mockResult);

        // When
        final String toString = mockResult.getResult().toString();
        final String ledgerToString = mockResult.getResult().toString();

        // Then
        assertThat(toString, startsWith("LedgersInformationResult"));
        assertThat(ledgerToString, startsWith("LedgersInformationResult.LedgersInformation"));
    }
}
