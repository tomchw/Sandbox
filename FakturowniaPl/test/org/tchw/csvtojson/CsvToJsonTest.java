package org.tchw.csvtojson;

import java.util.List;

import org.junit.Test;

import com.google.gson.JsonObject;

import static org.junit.Assert.assertEquals;

public class CsvToJsonTest {

	@Test
	public void test() {
		List<JsonObject> jsons = CsvToJsonConverter.builder()
				.buildForInputStream(CsvToJsonTest.class.getResourceAsStream("example.csv"))
				.resultAsListOfJsons();
		
		assertEquals("I was supposed to read one header and two objects", 2, jsons.size());
		JsonObject apple = jsons.get(0);
		JsonObject strawberry = jsons.get(1);
		assertEquals("120", apple.get("price_net").getAsString());
		assertEquals("Strawberry", strawberry.get("name").getAsString());
	}

}
