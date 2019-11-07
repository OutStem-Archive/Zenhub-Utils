package com.outreach.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpHelper {

    private static Logger log = Logger.getLogger(HttpHelper.class.getName());

    public static String RESPONSE_CODE = "response_code";
    public static String BODY = "body";

    public static HttpRequestBase addHeaders(HttpRequestBase httpMethod, Map<String, String> headers)
            throws IllegalArgumentException {
        if (httpMethod == null)
            throw new IllegalArgumentException("Cannot hava a null HTTP Object");

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }

        return httpMethod;
    }

    public static Map<String, Object> performPost(URL url, Map<String, String> headers, String body)
            throws IllegalArgumentException, ClientProtocolException, IOException {
        if (url == null)
            throw new IllegalArgumentException("Cannot have an empty URL");

        Map<String, Object> result = new HashMap<String, Object>();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        HttpPost httpPost = null;

        try {
            httpPost = new HttpPost(url.toString());
            httpPost.setEntity(new StringEntity(body));

            addHeaders(httpPost, headers);
            
            response = httpclient.execute(httpPost);

            int response_code = response.getStatusLine().getStatusCode();

            entity = response.getEntity();

            body = IOUtils.toString(entity.getContent(), "UTF-8");
            result.put("body", body);
            result.put("response_code", response_code);

            if (response_code / 100 != 2) {
                log.log(Level.SEVERE, "Received a " + response_code + " at " + url.toString());
            }

            return result;

        } finally {
            if (entity != null) {
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);
            }

            if (response != null) {
                response.close();
            }
        }

    }

    public static Map<String, Object> performGet(URL url, Map<String, String> headers)
            throws IllegalArgumentException, IOException {

        if (url == null)
            throw new IllegalArgumentException("Cannot have an empty URL");

        Map<String, Object> result = new HashMap<String, Object>();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url.toString());

        addHeaders(httpGet, headers);

        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String body = null;

        try {
            response = httpclient.execute(httpGet);

            int response_code = response.getStatusLine().getStatusCode();

            entity = response.getEntity();

            body = IOUtils.toString(entity.getContent(), "UTF-8");
            result.put("body", body);
            result.put("response_code", response_code);

            if (response_code / 100 != 2) {
                log.log(Level.SEVERE, "Received a " + response_code + " at " + url.toString());
            }

            return result;
        } finally {
            if (entity != null) {
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);
            }

            if (response != null) {
                response.close();
            }
        }
    }

    public static boolean wasRequestSuccessful(Map<String, Object> payload) {
        if (payload != null) {
            int response_code = (int) payload.get(HttpHelper.RESPONSE_CODE);
            return ((response_code / 100) == 2);
        }

        return false;
    }
}