package org.tchw.csvtojson;

import java.util.List;

import org.junit.Test;
import org.tchw.fakturownia.api.request.ApiToken;
import org.tchw.fakturownia.api.request.Payload;
import org.tchw.fakturownia.api.request.PostRequest;

import com.google.gson.JsonObject;

public class ProductPayloadTest {

	@Test
	public void test() {
		List<JsonObject> jsons = CsvToJsonConverter.builder()
				.buildForInputStream(CsvToJsonTest.class.getResourceAsStream("example.csv"))
				.resultAsListOfJsons();
		
		ApiToken token = ApiToken.create("sMEuDnemiZIPcbEL5g");
		for (JsonObject jsonObject : jsons) {
			importProduct(token, jsonObject);
		}
	}

	private void importProduct(ApiToken token, JsonObject jsonObject) {
		Payload jsonPayload = Payload.create(token, "product", jsonObject);
		PostRequest.create("http://tcc1.fakturownia.pl/products.json", jsonPayload.toJson()).execute();
	}
	
}
