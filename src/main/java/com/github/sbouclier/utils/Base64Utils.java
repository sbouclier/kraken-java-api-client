package com.github.sbouclier.utils;

import java.util.Base64;

/**
 * Base64 utility class
 *
 * @author Stéphane Bouclier
 */
public class Base64Utils {

    private Base64Utils() {
    }

    public static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
