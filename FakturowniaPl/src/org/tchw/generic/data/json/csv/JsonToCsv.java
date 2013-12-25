package org.tchw.generic.data.json.csv;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.tchw.generic.data.json.Json;
import org.tchw.generic.data.json.JsonArray;
import org.tchw.generic.data.json.csv.JsonArrayToCsvWriter.JsonArrayToCsvHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JsonToCsv {

    public static Json.From.JsonArrayPasser<FromJsonArray> takeFromJsonArray() {
        return new Json.From.JsonArrayPasser<FromJsonArray>() {
            @Override
            public FromJsonArray pass(JsonArray JsonArray) {
                return new FromJsonArray(JsonArray);
            }
        };
    }

    public static class FromJsonArray {

        private final JsonArray json;

        public FromJsonArray(JsonArray JsonArray) {
            this.json = JsonArray;
        }

        public void toFile(File file) {
            try {
                Files.write(jsonToCsv2(json), file, Charsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void toScreen() {
            System.out.println(jsonToCsv2(json));
        }

        public String asString() {
            return jsonToCsv2(json);
        }

    }

    private static String jsonToCsv2(JsonArray jsonArray) {
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
