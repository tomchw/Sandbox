package org.tchw.jsontocsv;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class SimpleJsonArrayToHeaderAndValuesWriter implements JsonArrayToCsvWriter {

    public static SimpleJsonArrayToHeaderAndValuesWriter DEFAULT = new SimpleJsonArrayToHeaderAndValuesWriter();

    private SimpleJsonArrayToHeaderAndValuesWriter() {
    }

    @Override
    public void process(JSONArray jsonArray, JsonArrayToCsvHandler handler) {
        ImmutableList<String> header = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ImmutableMap<String, String> jsonAsMap = jsonObjectAsMap(jsonObject);
                ImmutableList<String> keys = ImmutableList.copyOf( jsonAsMap.keySet() );
                if( i == 0 ) {
                    handler.onHeader(keys);
                    header = keys;
                } else {
                    checkIfKeysAreTheSameAsHeaders(header, keys);
                }
                handler.onObjectValues(ImmutableList.copyOf(jsonAsMap.values()));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkIfKeysAreTheSameAsHeaders(ImmutableList<String> header, ImmutableList<String> keys) {
        if( !header.equals(keys) ) {
            throw new IllegalStateException("\r\nHeader: " + header + "\r\nKeys  : " + keys);
        }
    }

    @SuppressWarnings("unchecked")
    private ImmutableMap<String, String> jsonObjectAsMap(JSONObject jsonObject) throws JSONException {
        Iterator<String> iterator = jsonObject.keys();
        ImmutableMap.Builder<String, String> jsonMapBuilder = ImmutableMap.builder();
        while(iterator.hasNext()) {
            String key = iterator.next();
            jsonMapBuilder.put(key, jsonObject.getString(key));
        }
        return jsonMapBuilder.build();
    }

}
