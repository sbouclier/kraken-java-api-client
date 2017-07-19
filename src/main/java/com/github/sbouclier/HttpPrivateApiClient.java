package com.github.sbouclier;

import com.github.sbouclier.result.Result;
import com.github.sbouclier.utils.Base64Utils;
import com.github.sbouclier.utils.ByteUtils;
import com.github.sbouclier.utils.CryptoUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

/**
 * Private Http API client
 *
 * @author Stéphane Bouclier
 */
public class HttpPrivateApiClient<T extends Result>  {

    private String apiKey;
    private String secret;

    public HttpPrivateApiClient(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    private String generateNonce() {
        return String.valueOf(System.currentTimeMillis() * 1000);
    }

    private String generateSignature(String path, String nonce, String postData) {
        // Algorithm: HMAC-SHA512 of (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key

        String hmacDigest = null;

        try {
            byte[] bytePath = ByteUtils.stringToBytes(path);
            byte[] sha256 = CryptoUtils.sha256(nonce + postData);
            byte[] hmacMessage = ByteUtils.concatArrays(bytePath, sha256);

            byte[] hmacKey = Base64Utils.base64Decode(this.secret);

            hmacDigest = Base64Utils.base64Encode(CryptoUtils.hmacSha512(hmacKey, hmacMessage));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return hmacDigest;
    }

    public String callUrl(String baseUrl, String urlMethod) throws IOException {
        final String nonce = generateNonce();
        final String postData = "nonce=" + nonce + "&";
        final String signature = generateSignature(urlMethod, nonce, postData);

        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(baseUrl + urlMethod).openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("API-Key", apiKey);
            connection.addRequestProperty("API-Sign", signature);

            if (postData != null && !postData.toString().isEmpty()) {
                connection.setDoOutput(true);
                try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                    out.write(postData.toString());
                }
            }

            //printHttpsCertificates(connection);

            // execute request and read response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            }
        } finally {
            connection.disconnect();
        }
    }

    private void printHttpsCertificates(HttpsURLConnection connection) {
        if (connection != null) {
            try {
                System.out.println("Response Code : " + connection.getResponseCode());
                System.out.println("Cipher Suite : " + connection.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = connection.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
