package org.tchw.data.json;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableSet;

public class JsonObject {

    private final JSONObject json;

    public static JsonObject create(JSONObject json) {
        return new JsonObject(json);
    }

    private JsonObject(JSONObject json) {
        this.json = json;
    }

    public String getString(String name) {
        try {
            return json.getString(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getInt(String name) {
        try {
            return json.getInt(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonObject getObject(String name) {
        try {
            return JsonObject.create(json.getJSONObject(name));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ImmutableSet<String> keys() {
        return ImmutableSet.<String>copyOf(json.keys());
    }

    public JsonArray getArray(String name) {
        try {
            return JsonArray.create(json.getJSONArray(name));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
