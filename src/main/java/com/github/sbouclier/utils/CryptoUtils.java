package com.github.sbouclier.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Cryptographic utility class
 *
 * @author Stéphane Bouclier
 */
public class CryptoUtils {

    /**
     * Private constructor
     */
    private CryptoUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Encode message to SHA-256 cryptographic hash algorithm
     *
     * @param message to encod
     * @return message encoded
     * @throws NoSuchAlgorithmException
     */
    public static byte[] sha256(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(ByteUtils.stringToBytes(message));
    }

    /**
     * Compute HMAC-SHA512 with secret key
     *
     * @param key     secret key
     * @param message to encod
     * @return generated HMAC-SHA512
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacSha512(byte[] key, byte[] message) throws InvalidKeyException, NoSuchAlgorithmException {
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(new SecretKeySpec(key, "HmacSHA512"));
        return mac.doFinal(message);
    }
}
