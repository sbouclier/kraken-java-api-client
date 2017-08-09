package com.github.sbouclier;

import com.github.sbouclier.utils.Base64Utils;
import com.github.sbouclier.utils.ByteUtils;
import com.github.sbouclier.utils.CryptoUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

/**
 * Http Json API client
 *
 * @author Stéphane Bouclier
 */
public class HttpJsonClient {

    private String apiKey;
    private String secret;

    // ----------------
    // - CONSTRUCTORS -
    // ----------------

    public HttpJsonClient() {
    }

    public HttpJsonClient(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    // -----------
    // - METHODS -
    // -----------

    public String executePublicQuery(String baseUrl, String urlMethod) throws IOException {
        return executePublicQuery(baseUrl, urlMethod, null);
    }

    public String executePublicQuery(String baseUrl, String urlMethod, Map<String, String> params) throws IOException {
        final StringBuilder url = new StringBuilder(baseUrl).append(urlMethod).append("?");

        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> {
                url.append(k).append("=").append(v).append("&");
            });
        }

        return getPublicJsonResponse(new URL(url.toString()));
    }

    public String getPublicJsonResponse(URL url) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            return getJsonResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    public String executePrivateQuery(String baseUrl, String urlMethod) throws IOException, KrakenApiException {
        return executePrivateQuery(baseUrl, urlMethod, null);
    }

    public String executePrivateQuery(String baseUrl, String urlMethod, Map<String, String> params) throws IOException, KrakenApiException {
        if (this.apiKey == null || this.secret == null) {
            throw new KrakenApiException("must provide API key and secret");
        }

        final String nonce = generateNonce();
        final String postData = buildPostData(params, nonce);
        final String signature = generateSignature(urlMethod, nonce, postData);

        return getPrivateJsonResponse(new URL(baseUrl + urlMethod), postData, signature);
    }

    public String getPrivateJsonResponse(URL url, String postData, String signature) throws IOException {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("API-Key", apiKey);
            connection.addRequestProperty("API-Sign", signature);

            if (postData != null && !postData.toString().isEmpty()) {
                connection.setDoOutput(true);
                try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                    out.write(postData.toString());
                }
            }

            return getJsonResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    private String buildPostData(Map<String, String> params, String nonce) {
        final StringBuilder postData = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> {
                postData.append(k).append("=").append(v).append("&");
            });
        }
        postData.append("nonce=").append(nonce);
        return postData.toString();
    }

    public String generateNonce() {
        return String.valueOf(System.currentTimeMillis() * 1000);
    }

    /**
     * Generate signature
     *
     * @param path     URI path
     * @param nonce
     * @param postData POST data
     * @return generated signature
     * @throws KrakenApiException
     */
    private String generateSignature(String path, String nonce, String postData) throws KrakenApiException {
        // Algorithm: HMAC-SHA512 of (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key

        String hmacDigest = null;

        try {
            byte[] bytePath = ByteUtils.stringToBytes(path);
            byte[] sha256 = CryptoUtils.sha256(nonce + postData);
            byte[] hmacMessage = ByteUtils.concatArrays(bytePath, sha256);

            byte[] hmacKey = Base64Utils.base64Decode(this.secret);

            hmacDigest = Base64Utils.base64Encode(CryptoUtils.hmacSha512(hmacKey, hmacMessage));
        } catch (Throwable ex) {
            throw new KrakenApiException("unable to generate signature");
        }
        return hmacDigest;
    }

    public String getJsonResponse(HttpsURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }
}
