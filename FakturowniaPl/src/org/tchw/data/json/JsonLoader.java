package org.tchw.data.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.tchw.data.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class JsonLoader<T> {

    public static <T> JsonLoader<T> create(Class<T> clazz) {
        return new JsonLoader<T>();
    }

    private void appendReader(Reader reader, JsonObjectTo<T> jsonObjectTo, ImmutableMap.Builder<String, T> builder) {
        JsonArray jsonArray = Json.takeFromReader().pass(Stream.toBufferedReader(reader)).asJsonArray().get();
        for (JsonObject jsonObject : jsonArray.getObjects()) {
            builder.put(jsonObject.getString("id"), jsonObjectTo.create(jsonObject));
        }
    }

    private final ImmutableList.Builder<Reader> readers = ImmutableList.builder();

    public JsonLoader<T> addFile(String filePath) {
        try {
            add(new InputStreamReader(new FileInputStream(filePath)));
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonLoader<T> add(Reader reader) {
        readers.add(reader);
        return this;
    }

    public ImmutableMap<String, T> build(JsonObjectTo<T> jsonObjectTo) {
        ImmutableMap.Builder<String, T> builder = ImmutableMap.builder();
        for (Reader reader : readers.build()) {
            appendReader(new BufferedReader(reader), jsonObjectTo, builder);
        }
        return builder.build();
    }

    public interface JsonObjectTo<T> {

        T create(JsonObject json);

    }
}

