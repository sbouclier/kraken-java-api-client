package com.github.sbouclier.mock;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.Certificate;

/**
 * HttpsURLConnection mock
 *
 * @author Stéphane Bouclier
 */
public class MockHttpsURLConnection extends HttpsURLConnection {
    public MockHttpsURLConnection(URL url) {
        super(url);
    }

    @Override
    public String getCipherSuite() {
        return null;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return new Certificate[0];
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream("read inputstream".getBytes("UTF-8"));
    }
}
