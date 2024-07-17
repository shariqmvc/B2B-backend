package com.korike.logistics.service.impl;

import java.net.URI;
import java.util.List;

import com.korike.logistics.common.Constants;
import com.korike.logistics.common.FakeSSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;



@SuppressWarnings("deprecation")
@Component
public class RestTemplateFactoryImpl {

    private List<HttpMessageConverter<?>> messageConverters;

    private ResponseErrorHandler errorHandler;

    public RestTemplate createRestTemplate(URI url, boolean ignoreSSL,
                                           boolean useProxy, String username, String password) {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory() {
            protected HttpContext createHttpContext(HttpMethod httpMethod,
                                                    URI uri) {
                // Create AuthCache instance
                AuthCache authCache = new BasicAuthCache();
                // Generate BASIC scheme object and add it to the local auth
                // cache
                BasicScheme basicAuth = new BasicScheme();
                authCache.put(
                        new HttpHost(uri.getHost(), getPort(uri), uri
                                .getScheme()), basicAuth);
                // Add AuthCache to the execution context
                BasicHttpContext localcontext = new BasicHttpContext();
                localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
                return localcontext;
            }
        };

        factory.setReadTimeout(0);
        factory.setConnectTimeout(0);

        HttpClient client = factory.getHttpClient();

        // disable request retry. Application will retry if required.
        if (client instanceof AbstractHttpClient) {
            ((AbstractHttpClient) client)
                    .setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
                            0, false));
        }

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(errorHandler);
        restTemplate.setMessageConverters(messageConverters);

        if (url.getScheme().equals(Constants.HTTPS_PROTOCOL) && ignoreSSL) {
            Scheme scheme = new Scheme(url.getScheme(), getPort(url),
                    FakeSSLSocketFactory.getInstance());
            client.getConnectionManager().getSchemeRegistry().register(scheme);
        }

        if (url.getScheme().equals(Constants.HTTP_PROTOCOL)) {
            Scheme scheme = new Scheme(url.getScheme(), getPort(url),
                    PlainSocketFactory.getSocketFactory());
            client.getConnectionManager().getSchemeRegistry().register(scheme);
        }

        // Set basic auth credentials if present.
        if (StringUtils.isNotEmpty(username)
                && StringUtils.isNotEmpty(password)
                && client instanceof AbstractHttpClient) {
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                    username, password);
            AuthScope authscope = new AuthScope(url.getHost(), url.getPort(),
                    AuthScope.ANY_REALM);

            ((AbstractHttpClient) client).getCredentialsProvider()
                    .setCredentials(authscope, credentials);
        }

        return restTemplate;
    }

    public void setMessageConverters(
            List<HttpMessageConverter<?>> messageConverters) {
        this.messageConverters = messageConverters;
    }

    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private int getPort(URI uri) {
        if (uri.getScheme().equals(Constants.HTTPS_PROTOCOL)) {
            return 443;
        } else {
            return 80;
        }
    }

}