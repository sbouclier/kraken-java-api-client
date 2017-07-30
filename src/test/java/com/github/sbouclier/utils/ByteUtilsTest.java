package com.github.sbouclier.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * Byte utility test
 *
 * @author Stéphane Bouclier
 */
public class ByteUtilsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void utilityClassTest() throws Throwable {
        final Constructor<ByteUtils> constructor = ByteUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    @Test
    public void should_convert_string_and_bytes() {
        final String data = "my data to encode and decode";
        Assert.assertEquals(data, ByteUtils.bytesToString(ByteUtils.stringToBytes(data)));
    }

    @Test
    public void should_concatenate_bytes_arrays() throws IOException {
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {4, 5, 6};

        Assert.assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, ByteUtils.concatArrays(array1, array2));
    }
}
