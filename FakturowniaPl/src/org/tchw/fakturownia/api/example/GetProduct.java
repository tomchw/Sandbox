package org.tchw.fakturownia.api.example;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.io.CharStreams;



public class GetProduct {

	public void execute() {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpGet get = new HttpGet("https://tcc1.fakturownia.pl/products.json?api_token=sMEuDnemiZIPcbEL5g");
			CloseableHttpResponse response = httpClient.execute(get);
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
	
	public static void main(String[] args) {
		new GetProduct().execute();
	}
	
}
