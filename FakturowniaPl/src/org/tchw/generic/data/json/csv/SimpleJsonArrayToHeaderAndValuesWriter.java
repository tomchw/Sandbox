package org.tchw.generic.data.json.csv;

import org.json.JSONException;
import org.tchw.generic.data.json.JsonArray;
import org.tchw.generic.data.json.JsonObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;

class SimpleJsonArrayToHeaderAndValuesWriter implements JsonArrayToCsvWriter {

    public static SimpleJsonArrayToHeaderAndValuesWriter DEFAULT = new SimpleJsonArrayToHeaderAndValuesWriter();

    private SimpleJsonArrayToHeaderAndValuesWriter() {
    }

    @Override
    public void process(JsonArray jsonArray, JsonArrayToCsvHandler handler) {
        ImmutableList<String> header = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JsonObject jsonObject = jsonArray.getObject(i);
                ImmutableSortedMap<String, String> jsonAsMap = jsonObjectAsMap(jsonObject);
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
    private ImmutableSortedMap<String, String> jsonObjectAsMap(JsonObject jsonObject) throws JSONException {
        ImmutableSet<String> keys = jsonObject.keys();
        ImmutableSortedMap.Builder<String, String> jsonMapBuilder = ImmutableSortedMap.naturalOrder();
        for (String key : keys) {
            jsonMapBuilder.put(key, jsonObject.getString(key));
        }
        return jsonMapBuilder.build();
    }

}
