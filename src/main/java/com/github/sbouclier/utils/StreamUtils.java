package com.github.sbouclier.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Stream utility class
 *
 * @author Stéphane Bouclier
 */
public class StreamUtils {

    /**
     * Private constructor
     */
    private StreamUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Load a resource as string
     *
     * @param clazz
     * @param path
     * @return string
     * @throws IOException
     */
    public static String getResourceAsString(Class clazz, String path) throws IOException {
        return convert(getResourceAsStream(clazz, path));
    }

    /**
     * Load a resource as input stream
     *
     * @param path
     * @return resource as input stream
     */
    public static InputStream getResourceAsStream(Class clazz, String path) {
        return clazz.getClassLoader().getResourceAsStream(path);
    }

    /**
     * Convert an input stream to string
     *
     * @param inputStream
     * @return input stream as string
     * @throws IOException
     */
    public static String convert(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        return new String(byteArray, StandardCharsets.UTF_8);
    }
}
