package com.korike.logistics.common;

import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;

@SuppressWarnings("deprecation")
public class FakeSSLSocketFactory extends SSLSocketFactory {

    private static FakeSSLSocketFactory instance;

    static {
        try {
            instance = new FakeSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private FakeSSLSocketFactory() throws KeyManagementException,
            UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException {

        super(new TrustStrategy() {
            public boolean isTrusted(final X509Certificate[] chain,
                                     final String authType) throws CertificateException {
                return true;
            }
        }, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

    }

    public static FakeSSLSocketFactory getInstance() {
        return instance;
    }
}
