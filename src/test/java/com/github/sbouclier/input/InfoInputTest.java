package com.github.sbouclier.input;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * InfoInput test
 *
 * @author Stéphane Bouclier
 */
public class InfoInputTest {

    @Test
    public void should_return_value_of() throws IOException {
        assertThat(InfoInput.ALL, equalTo(InfoInput.valueOf("ALL")));
        assertThat(InfoInput.MARGIN, equalTo(InfoInput.valueOf("MARGIN")));
    }
}
