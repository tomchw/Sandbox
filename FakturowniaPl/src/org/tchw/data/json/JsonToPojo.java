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

public class JsonToPojo<T> {

    public static <T> JsonToPojo<T> create(Class<T> clazz) {
        return new JsonToPojo<T>();
    }

    public interface JsonObjectTo<T> {

        T create(JsonObject json);
    }

    private final ImmutableList.Builder<Reader> readers = ImmutableList.builder();

    public JsonToPojo<T> addFile(String filePath) {
        try {
            add(new InputStreamReader(new FileInputStream(filePath)));
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonToPojo<T> add(Reader reader) {
        readers.add(reader);
        return this;
    }

    public JsonToPojo<T> addAll(Iterable<? extends Reader> readers) {
        this.readers.addAll(readers);
        return this;
    }

    public ImmutableMap<String, T> build(JsonObjectTo<T> jsonObjectTo) {
        ImmutableMap.Builder<String, T> builder = ImmutableMap.builder();
        for (Reader reader : readers.build()) {
            appendReader(new BufferedReader(reader), jsonObjectTo, builder);
        }
        return builder.build();
    }

    private void appendReader(Reader reader, JsonObjectTo<T> jsonObjectTo, ImmutableMap.Builder<String, T> builder) {
        Object value = JSONTokenerNextValue(reader);
        if( value instanceof JSONArray ) {
            JsonArray jsonArray = JsonArray.create((JSONArray) value);
            for (JsonObject jsonObject : jsonArray.getObjects()) {
                builder.put(jsonObject.getString("id"), jsonObjectTo.create(jsonObject));
            }
        } else if( value instanceof JSONObject ) {
            JsonObject jsonObject = JsonObject.create((JSONObject) value);
            builder.put(jsonObject.getString("id"), jsonObjectTo.create(jsonObject));
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

}

