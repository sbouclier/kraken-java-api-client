package com.github.sbouclier.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

/**
 * Crypto utility test
 *
 * @author Stéphane Bouclier
 */
public class CryptoUtilsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void utilityClassTest() throws Throwable {
        final Constructor<CryptoUtils> constructor = CryptoUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    @Test
    public void should_encode_message_to_sha256() throws NoSuchAlgorithmException {
        String message = "encode massage to SHA-256";
        String expectedEncodedBase64Message = "nEZ+FrTR+yvroKsc7RZ5LXq+hhyRnHuYgMCrlu+GaJI=";

        byte[] result = CryptoUtils.sha256(message);

        Assert.assertEquals(expectedEncodedBase64Message, Base64Utils.base64Encode(result));
    }

    @Test
    public void should_encode_message_to_hmac_sha512() throws NoSuchAlgorithmException, InvalidKeyException {
        String key = "key";
        String message = "encode massage to HMAC SHA-512";
        String expectedEncodedBase64Message = "5hMakkwKQG7fuPcX5ZuxH1qDSSpTsP/k0+O7cKVgYoLPRb8j4ZqH7w+un075d8QYvfDub+SACgVS97cg1aAkUQ==";

        byte[] result = CryptoUtils.hmacSha512(ByteUtils.stringToBytes(key), ByteUtils.stringToBytes(message));

        Assert.assertEquals(expectedEncodedBase64Message, Base64Utils.base64Encode(result));
    }
}
