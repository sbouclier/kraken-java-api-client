package com.github.sbouclier.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * Stream utility test
 *
 * @author Stéphane Bouclier
 */
public class StreamUtilsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void utilityClassTest() throws Throwable {
        final Constructor<StreamUtils> constructor = StreamUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
