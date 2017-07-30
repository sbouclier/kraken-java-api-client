package com.github.sbouclier.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Byyte utility class
 *
 * @author Stéphane Bouclier
 */
public class ByteUtils {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    /**
     * Private constructor
     */
    private ByteUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert string to bytes using UTF-8 charset
     *
     * @param input to convert
     * @return converted string to bytes array
     */
    public static byte[] stringToBytes(String input) {
        return input.getBytes(UTF8_CHARSET);
    }

    /**
     * Convert bytes array to string using UTF-8 charset
     *
     * @param bytes array to convert
     * @return converted bytes array to string
     */
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    /**
     * Concatenate arrays of bytes into one
     *
     * @param a first array
     * @param b second array
     * @return array of bytes concatenated
     */
    public static byte[] concatArrays(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }
}
