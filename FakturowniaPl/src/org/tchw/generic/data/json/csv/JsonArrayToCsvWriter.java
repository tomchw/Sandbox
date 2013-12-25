package org.tchw.generic.data.json.csv;

import java.util.List;

import org.tchw.generic.data.json.JsonArray;

interface JsonArrayToCsvWriter {

    void process(JsonArray jsonArray, JsonArrayToCsvHandler handler);

    public interface JsonArrayToCsvHandler {

        void onHeader(List<String> headers);

        void onObjectValues(List<String> values);

    }

}
