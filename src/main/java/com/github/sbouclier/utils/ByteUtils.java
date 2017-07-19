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

    private ByteUtils() {
    }

    public static byte[] stringToBytes(String input) {
        return input.getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] concatArrays(byte[] a, byte[] b) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( a );
            outputStream.write( b );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray( );
    }
}
