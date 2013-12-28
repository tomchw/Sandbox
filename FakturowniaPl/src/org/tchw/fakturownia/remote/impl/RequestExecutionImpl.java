package org.tchw.fakturownia.remote.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.tchw.fakturownia.remote.ResponseContentHandling;
import org.tchw.fakturownia.remote.RequestExecution;

public class RequestExecutionImpl implements RequestExecution {

    public void execute(String url, ResponseContentHandling contentHandling) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(get);
            handleContentAndClose(contentHandling, response);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(httpClient);
        }
    }

    private void handleContentAndClose(ResponseContentHandling contentHandling, CloseableHttpResponse response) throws IOException {
        BufferedReader contentAsReader = new BufferedReader( new InputStreamReader( response.getEntity().getContent() ));
        try {
            contentHandling.handleContent(contentAsReader);
        } finally {
            closeQuietly(contentAsReader);
        }
    }

    private void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}