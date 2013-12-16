package org.tchw.jsontocsv;

import java.util.List;

import org.json.JSONArray;

public interface JsonArrayToCsvWriter {

    void process(JSONArray jsonArray, JsonArrayToCsvHandler handler);

    public interface JsonArrayToCsvHandler {

        void onHeader(List<String> headers);

        void onObjectValues(List<String> values);

    }

}
