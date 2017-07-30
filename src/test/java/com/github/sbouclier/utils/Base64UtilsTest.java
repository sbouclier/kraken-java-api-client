package com.github.sbouclier.utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Base64 utility test
 *
 * @author Stéphane Bouclier
 */
public class Base64UtilsTest {

    @Test
    public void should_encode_and_decode_data() {
        String originalInput = "my data 123 to encode";

        String encode = Base64Utils.base64Encode(originalInput.getBytes());
        byte[] decode = Base64Utils.base64Decode(encode);

        assertEquals(originalInput, new String(decode));
    }
}
