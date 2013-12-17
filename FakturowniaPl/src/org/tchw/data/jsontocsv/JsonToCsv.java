package org.tchw.data.jsontocsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.tchw.data.json.Json;
import org.tchw.data.jsontocsv.JsonArrayToCsvWriter.JsonArrayToCsvHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JsonToCsv {

    public static Json.From.Passer<From> takeFromJson() {
        return new Json.From.Passer<From>() {
            @Override
            public From pass(BufferedReader reader) {
                return new From(reader);
            }
        };
    }

    public static class From {

        private final BufferedReader reader;

        public From(BufferedReader reader) {
            this.reader = reader;
        }

        public Execution toFile(File file) {
            return new Execution(jsonToCsvAndPrintToFile(file));
        }

        public Execution toScreen() {
            return new Execution(jsonToCsvAndPrintToScreen());
        }

        public String asStringNow() {
            final StringBuilder builder = new StringBuilder();
            new Execution(new JsonArrayHandling() {
                @Override
                public void handle(JSONArray jsonArray) {
                    builder.append(jsonToCsv2(jsonArray));
                }
            }).executeSync();
            return builder.toString();
        }

        public class Execution {

            private final JsonArrayHandling jsonArrayHandling;

            public Execution(JsonArrayHandling jsonArrayHandling) {
                this.jsonArrayHandling = jsonArrayHandling;
            }

            public void executeSync() {
                JSONArray jsonArray = asJSONArray(reader);
                jsonArrayHandling.handle(jsonArray);
            }

        }
    }

    private interface JsonArrayHandling {

        void handle(JSONArray jsonArray);

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

    private static JsonArrayHandling jsonToCsvAndPrintToScreen() {
        return new JsonArrayHandling() {
            @Override
            public void handle(JSONArray jsonArray) {
                System.out.println(jsonToCsv2(jsonArray));
            }
        };
    }

    private static JsonArrayHandling jsonToCsvAndPrintToFile(final File file) {
        return new JsonArrayHandling() {
            @Override
            public void handle(JSONArray jsonArray) {
                try {
                    Files.write(jsonToCsv2(jsonArray), file, Charsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private static JSONArray asJSONArray(BufferedReader reader) {
        try {
            return new JSONArray(new JSONTokener(reader));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
