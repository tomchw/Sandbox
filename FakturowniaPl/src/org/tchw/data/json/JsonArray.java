package org.tchw.data.json;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonArray {

    private final JSONArray json;

    public static JsonArray create(JSONArray json) {
        return new JsonArray(json);
    }

    private JsonArray(JSONArray json) {
        this.json = json;
    }

    public JsonObject getObject(int i) {
        try {
            return JsonObject.create(json.getJSONObject(i));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int length() {
        return json.length();
    }
}
