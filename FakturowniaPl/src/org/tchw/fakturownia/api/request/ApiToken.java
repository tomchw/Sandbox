package org.tchw.fakturownia.api.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiToken {

	private JsonElement apitoken;
	
	public static ApiToken create(String apitoken) {
		return new ApiToken(apitoken);
	}
	
	private ApiToken(String p_apitoken) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("api_token", p_apitoken);
		apitoken = jsonObject; 
	}
	
	public JsonElement toJson() {
		return apitoken;
	}
	
}
