package com.github.sbouclier.input;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Interval test
 *
 * @author Stéphane Bouclier
 */
public class IntervalTest {

    @Test
    public void should_return_value_of() throws IOException {
        assertThat(Interval.ONE_DAY, equalTo(Interval.valueOf("ONE_DAY")));
    }
}