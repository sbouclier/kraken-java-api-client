package com.github.sbouclier.result;

import com.github.sbouclier.mock.MockInitHelper;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertTrue;

/**
 * RecentSpreadResult test
 *
 * @author Stéphane Bouclier
 */
public class RecentSpreadResultTest {

    @Test
    public void should_not_access_private_constructor() throws Throwable {
        final Constructor<RecentSpreadResult.Spread> constructor = RecentSpreadResult.Spread.class.getDeclaredConstructor();

        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        RecentSpreadResult mockResult = MockInitHelper.buildRecentSpreadResult();

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("RecentSpreadResult"));
    }
}
