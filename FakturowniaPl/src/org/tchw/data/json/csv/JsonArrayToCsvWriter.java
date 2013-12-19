package org.tchw.data.json.csv;

import java.util.List;

import org.tchw.data.json.JsonArray;

interface JsonArrayToCsvWriter {

    void process(JsonArray jsonArray, JsonArrayToCsvHandler handler);

    public interface JsonArrayToCsvHandler {

        void onHeader(List<String> headers);

        void onObjectValues(List<String> values);

    }

}
