package com.outreach.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpHelper {

    private static Logger log = Logger.getLogger(HttpHelper.class.getName());

    public static HttpRequestBase addHeaders(HttpRequestBase httpMethod, Map<String, String> headers) throws IllegalArgumentException {
        if (httpMethod == null)
            throw new IllegalArgumentException("Cannot hava a null HTTP Object");
    
		if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }

        return httpMethod;
	}

    public static String performGet(URL url, Map<String, String> headers) throws IOException {

        if (url == null)
            throw new IllegalArgumentException("Cannot have an empty URL");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url.toString());

        addHeaders(httpGet, headers);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = null;
        String body = null;

        try {
            int response_code = response.getStatusLine().getStatusCode();

            if (response_code / 100 == 2) {
                entity = response.getEntity();

                body = IOUtils.toString(entity.getContent(), "UTF-8");
            } else {
                log.log(Level.SEVERE, "Received a " + response_code + " at " + url.toString());
            }

            return body;
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
}