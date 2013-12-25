package org.tchw.generic.data.json;

import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import com.google.common.collect.ImmutableList;

public class JsonArray {

    private final JSONArray json;

    public static JsonArray create(JSONArray json) {
        return new JsonArray(json);
    }

    public static JsonArray create(Reader reader) {
        try {
            return new JsonArray( new JSONArray(new JSONTokener(reader)) ) ;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonArray(JSONArray json) {
        this.json = json;
    }

    public ImmutableList<JsonObject> getObjects() {
        ImmutableList.Builder<JsonObject> builder = ImmutableList.builder();
        for (int i = 0; i < json.length(); i++) {
            builder.add(getObject(i));
        }
        return builder.build();
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
