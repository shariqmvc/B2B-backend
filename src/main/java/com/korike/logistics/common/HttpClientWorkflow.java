package com.korike.logistics.common;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class HttpClientWorkflow {


    private static final Logger logger = LogManager.getLogger(HttpClientWorkflow.class);
    public static String executeGetRequests(String url, String payload, String contentTypeHeader, String acceptHeader) throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        try {

            HttpGet request = new HttpGet(url);

            // add request headers
            if(acceptHeader!=null) {
                request.addHeader("Accept", acceptHeader);
            }
            if(contentTypeHeader!=null) {
                request.addHeader("Content-Type", contentTypeHeader);
            }
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    return result;
                }

            } finally {
                response.close();
                return response.getStatusLine().toString();
            }

        } finally {
            httpClient.close();
        }

    }

    public String executeWebClientPost(String url, JSONObject jsonPayload) throws URISyntaxException {
        WebClient webClient = null;
        try {
            ClientResponse response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonPayload)
                    .exchange()
                    .block();

            System.out.println(response);
        }
        catch(Exception e){

        }
        return  "";
    }



    public static JSONObject executePostRequestsHresponse (String url, JSONObject jsonPayload, JSONArray arrayPayload, String credentials, Boolean basicAuth, String sessionsKey, Boolean returnBody, Boolean returnHeader) throws
            IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, JSONException {


        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        JSONObject myObject;
        try {

            HttpPost request = new HttpPost(url);

            // add request headers
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            if(basicAuth){
                request.addHeader("Authorization", "Basic "+credentials);
            }else if(sessionsKey!=null){
                request.addHeader("x-hm-authorization", sessionsKey);
            }
            if(jsonPayload!=null) {
                StringBuilder json = new StringBuilder();
                json.append(jsonPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request);
            }
            else if(arrayPayload!=null){
                StringBuilder json = new StringBuilder();
                json.append(arrayPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request);
            }

//                String json_string = response.getEntity().getContent().toString();
//                myObject = new JSONObject(json_string);
            try {
                if(returnHeader) {
                    Header[] headers = response.getAllHeaders();
                    if (headers != null) {
                        for(Header header: headers){
                            result.put(header.getName(), header.getValue());
                        }
                    }
                    // Get HttpResponse Status
                    response.getStatusLine().getStatusCode();   // 200
                    response.getStatusLine().getReasonPhrase(); // OK
//                    HttpEntity entity = response.getEntity();
//                    if (entity != null) {
//                        // return it as a String
//                        result = EntityUtils.toString(entity);
//                        System.out.println(result);
//                    }

                }
                else if(returnBody){
                    result = new JSONObject(response.getEntity().getContent().toString());
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

        return result;
    }

    public static JSONObject executePostRequests (String url, JSONObject jsonPayload, JSONArray arrayPayload, String credentials, Boolean basicAuth, String sessionsKey, Boolean returnBody, Boolean returnHeader) throws
            IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, JSONException {


        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        JSONObject result = new JSONObject();
        String response = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        JSONObject myObject;
        try {
            HttpPost request = new HttpPost(url);

            // add request headers
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            if(basicAuth){
                request.addHeader("Authorization", "Basic "+credentials);
            }else if(sessionsKey!=null){
                request.addHeader("x-hm-authorization", sessionsKey);
            }
            if(jsonPayload!=null) {
                StringBuilder json = new StringBuilder();
                json.append(jsonPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            else if(arrayPayload!=null){
                StringBuilder json = new StringBuilder();
                json.append(arrayPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            result = new JSONObject(response.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            httpClient.close();
        }
        return result;
    }

    public static JSONObject executeGetRequestsForRBody (String url, JSONObject jsonPayload, JSONArray arrayPayload, String credentials, Boolean basicAuth, String sessionsKey) throws
            IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, JSONException {


        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        JSONObject result = new JSONObject();
        String response = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        JSONObject myObject;
        try {
            HttpPost request = new HttpPost(url);

            // add request headers
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            if(basicAuth){
                request.addHeader("Authorization", "Basic "+credentials);
            }else if(sessionsKey!=null){
                request.addHeader("x-hm-authorization", sessionsKey);
            }
            if(jsonPayload!=null) {
                StringBuilder json = new StringBuilder();
                json.append(jsonPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            else if(arrayPayload!=null){
                StringBuilder json = new StringBuilder();
                json.append(arrayPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            else{
                response = httpClient.execute(request, responseHandler);
            }
            result = new JSONObject(response.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            httpClient.close();
        }
        return result;
    }

    public static JSONObject executePostRequestsWithoutAuth (String url, JSONObject jsonPayload, JSONArray arrayPayload, Boolean returnBody, Boolean returnHeader) throws
            IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, JSONException {


        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        JSONObject result = new JSONObject();
        String response = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        JSONObject myObject;
        try {
            HttpPost request = new HttpPost(url);

            // add request headers
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            if(jsonPayload!=null) {
                StringBuilder json = new StringBuilder();
                json.append(jsonPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            else if(arrayPayload!=null){
                StringBuilder json = new StringBuilder();
                json.append(arrayPayload);
                request.setEntity(new StringEntity(json.toString()));
                response = httpClient.execute(request, responseHandler);
            }
            result = new JSONObject(response.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            httpClient.close();
        }
        return result;
    }

    public static String executePostMultiForm (String url, String textBodyName, String textBodyValue, String nspUserPwd) throws
            IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {


        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        String result = "";

        try {

            HttpPost request = new HttpPost(url);
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addTextBody(textBodyName, textBodyValue)
                    .build();
            // add request headers
            request.addHeader("Accept", "application/json");
            request.addHeader("Authorization", "Basic "+nspUserPwd);
//            StringBuilder json = new StringBuilder();
//            json.append(jsonString);
//            request.setEntity(new StringEntity(json.toString()));
            request.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(request);
            try {

                // Get HttpResponse Status
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                result = response.toString();
//                Header[] headers = response.getHeaders("x-hm-authorization");
//                result = headers[0].getValue();
//                    HttpEntity entity = response.getEntity();
//                    if (entity != null) {
//                        // return it as a String
//                        result = EntityUtils.toString(entity);
//                        System.out.println(result);
//                    }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return result;
    }

    public static void main(String[] args) throws JSONException, URISyntaxException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        HttpClientWorkflow client = new HttpClientWorkflow();
        String url = Constants.SMS_GATEWAY_BASE_URL_WITH_KEY+"9945335164"+"/"+"123456"+"/"+Constants.SMS_GATEWAY_SUFFIX_WITH_TEMPLATE;
        HttpClientWorkflow.executeGetRequests(url, null, Constants.SMS_GATEWAY_HEADER_CONTENT_TYPE, null);
    }
}

