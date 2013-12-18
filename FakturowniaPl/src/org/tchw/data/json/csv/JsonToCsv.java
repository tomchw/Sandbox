package org.tchw.data.json.csv;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.json.JSONArray;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.tchw.data.json.Json;
import org.tchw.data.json.csv.JsonArrayToCsvWriter.JsonArrayToCsvHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JsonToCsv {

    public static Json.From.JSONArrayPasser<FromJSONArray> takeFromJSONArray() {
        return new Json.From.JSONArrayPasser<FromJSONArray>() {
            @Override
            public FromJSONArray pass(JSONArray jsonArray) {
                return new FromJSONArray(jsonArray);
            }
        };
    }

    public static class FromJSONArray {

        private final JSONArray jsonArray;

        public FromJSONArray(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

        public void toFile(File file) {
            try {
                Files.write(jsonToCsv2(jsonArray), file, Charsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void toScreen() {
            System.out.println(jsonToCsv2(jsonArray));
        }

        public String asString() {
            return jsonToCsv2(jsonArray);
        }

    }

    private static String jsonToCsv2(JSONArray jsonArray) {
        StringWriter stringWriter = new StringWriter();
        final CsvListWriter csvListWriter = new CsvListWriter(stringWriter, CsvPreference.STANDARD_PREFERENCE);
        try {
            SimpleJsonArrayToHeaderAndValuesWriter.DEFAULT.process(jsonArray, new JsonArrayToCsvHandler() {
                @Override
                public void onHeader(List<String> headers) {
                    try {
                        csvListWriter.write(headers);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                @Override
                public void onObjectValues(List<String> values) {
                    try {
                        csvListWriter.write(values);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } finally {
            closeQuietly(csvListWriter);
        }
        return stringWriter.toString();
    }

    private static void closeQuietly(final CsvListWriter csvListWriter) {
        try {
            csvListWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
