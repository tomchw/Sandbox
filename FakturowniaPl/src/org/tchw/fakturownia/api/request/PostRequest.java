package org.tchw.fakturownia.api.request;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.io.CharStreams;
import com.google.gson.JsonElement;

public class PostRequest {

	private final String url;
	private final JsonElement content;

	public static PostRequest create(String url, JsonElement content) {
		return new PostRequest(url,  content);
	}
	
	private PostRequest(String url, JsonElement content) {
		this.url = url;
		this.content = content;
	}
	
	public void execute() {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost post = new HttpPost(url);
			post.addHeader("Accept", "application/json");  
			post.addHeader("Content-Type", "application/json");  
			post.setEntity(new StringEntity(content.toString()));
			
			System.out.println(post);
			System.out.println(content);
			System.out.println("---");
			
			CloseableHttpResponse response = httpClient.execute(post);
			List<String> lines = readContentAndClose(response);
			for (String line : lines) {
				System.out.println(line);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(httpClient);
		}
	}

	private List<String> readContentAndClose(CloseableHttpResponse response)
			throws IOException {
		Reader content = new InputStreamReader( response.getEntity().getContent() );
		try {
			try {
				return CharStreams.readLines(content);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			closeQuietly(content);
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
