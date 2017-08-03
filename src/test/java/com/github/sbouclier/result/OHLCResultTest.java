package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.MockInitHelper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * OHLCResult test
 *
 * @author Stéphane Bouclier
 */
public class OHLCResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        OHLCResult mockResult = MockInitHelper.buildOHLCResult();

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("OHLCResult"));
    }
}
