package org.tchw.data.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tchw.data.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class JsonToPojo {

    public static JsonToPojo create() {
        return new JsonToPojo();
    }

    public interface JsonObjectTo<T> {

        T create(JsonObject json);
    }

    private final ImmutableList.Builder<Reader> readers = ImmutableList.builder();

    public JsonToPojo addFile(String filePath) {
        try {
            add(new InputStreamReader(new FileInputStream(filePath)));
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonToPojo add(Reader reader) {
        readers.add(reader);
        return this;
    }

    public JsonToPojo addAll(Iterable<? extends Reader> readers) {
        this.readers.addAll(readers);
        return this;
    }

    public <T> ImmutableMap<String, T> buildAsMap(JsonObjectTo<T> jsonObjectTo) {
        return buildAsMap(jsonObjectTo, "id");
    }

    public <T> ImmutableMap<String, T> buildAsMap(JsonObjectTo<T> jsonObjectTo, String idFieldName) {
        ImmutableMap.Builder<String, T> builder = ImmutableMap.builder();
        for (Reader reader : readers.build()) {
            jsonObjectToPojoMap(new BufferedReader(reader), jsonObjectTo, builder, idFieldName);
        }
        return builder.build();
    }

    private <T> void jsonObjectToPojoMap(Reader reader, final JsonObjectTo<T> jsonObjectTo, final ImmutableMap.Builder<String, T> builder, final String idFieldName) {
        Handling mapWithIdHandling = new Handling() {
            @Override
            public void onParsedJsonObject(JsonObject jsonObject) {
                builder.put(jsonObject.getString(idFieldName), jsonObjectTo.create(jsonObject));
            }
        };
        readJsonObjects(reader, mapWithIdHandling);
    }

    private void readJsonObjects(Reader reader, Handling handling) {
        Object value = JSONTokenerNextValue(reader);
        if( value instanceof JSONArray ) {
            JsonArray jsonArray = JsonArray.create((JSONArray) value);
            for (JsonObject jsonObject : jsonArray.getObjects()) {
                handling.onParsedJsonObject(jsonObject);
            }
        } else if( value instanceof JSONObject ) {
            JsonObject jsonObject = JsonObject.create((JSONObject) value);
            handling.onParsedJsonObject(jsonObject);
        }
    }

    private Object JSONTokenerNextValue(Reader reader) {
        Object value;
        try {
            value = Json.takeFromReader().pass(Stream.toBufferedReader(reader)).asJSONTokener().nextValue();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private interface Handling {

        void onParsedJsonObject(JsonObject jsonObject);

    }
}

