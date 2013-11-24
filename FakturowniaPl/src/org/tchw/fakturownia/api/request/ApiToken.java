package org.tchw.fakturownia.api.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiToken {

	private final JsonElement apitoken;
	
	public final String name = "api_token";
	public final String value;
	
	public static ApiToken create(String apitoken) {
		return new ApiToken(apitoken);
	}
	
	private ApiToken(String p_apitoken) {
		value = p_apitoken;
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(name, value);
		apitoken = jsonObject; 
	}
	
	public JsonElement toJson() {
		return apitoken;
	}
	
}
