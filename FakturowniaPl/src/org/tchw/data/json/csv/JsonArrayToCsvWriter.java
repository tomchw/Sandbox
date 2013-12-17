package org.tchw.data.json.csv;

import java.util.List;

import org.json.JSONArray;

interface JsonArrayToCsvWriter {

    void process(JSONArray jsonArray, JsonArrayToCsvHandler handler);

    public interface JsonArrayToCsvHandler {

        void onHeader(List<String> headers);

        void onObjectValues(List<String> values);

    }

}
