package org.tchw.fakturownia.api.request;

import com.google.gson.JsonObject;

public class Payload {

	private final JsonObject json;

	public static Payload create(ApiToken token, String jsonName, JsonObject jsonObject) {
		return new Payload(token, jsonName, jsonObject);
	}
	
	private Payload(ApiToken token, String jsonName, JsonObject jsonObject) {
		json = new JsonObject();
		json.addProperty(token.name, token.value);
		json.add(jsonName, jsonObject);
	}
	
	public JsonObject toJson() {
		return json;
	}
}
