package org.tchw.fakturownia.remote;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

class ExecuteRequest {

    private final String url;
    private final ContentHandlingWithBufferedReader contentHandling;

    ExecuteRequest(String url, ContentHandlingWithBufferedReader contentHandling) {
        this.url = url;
        this.contentHandling = contentHandling;
    }
    
    public void executeSync() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(get);
            handleContentAndClose(response);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(httpClient);
        }
    }

    private void handleContentAndClose(CloseableHttpResponse response) throws IOException {
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
    
    interface ContentHandlingWithBufferedReader {
        
        void handleContent(BufferedReader reader);
        
    }
}