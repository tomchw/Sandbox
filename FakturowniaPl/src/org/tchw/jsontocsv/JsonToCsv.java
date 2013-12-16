package org.tchw.jsontocsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.tchw.jsontocsv.JsonArrayToCsvWriter.JsonArrayToCsvHandler;
import org.tchw.jsontocsv.JsonToCsv.From.Execution;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

public class JsonToCsv {

    public static From fromStream(InputStream input) {
        return new From(new BufferedReader(new InputStreamReader(input)));
    }

    public static From fromFile(String filePath) {
        try {
            return new From(new BufferedReader(new FileReader(new File(filePath))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Execution fromFileToSimilarFile(String filePath) {
        return fromFile(filePath).toFile(new File(filePath + ".csv"));
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

    //    private static String jsonToCsv(JSONArray jsonArray) {
    //        try {
    //            return CDL.toString(jsonArray);
    //        } catch (JSONException e) {
    //            throw new RuntimeException(e);
    //        }
    //    }

    private static String jsonToCsv2(JSONArray jsonArray) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        SimpleJsonArrayToHeaderAndValuesWriter.DEFAULT.process(jsonArray, new JsonArrayToCsvHandler() {
            final Joiner commaJoiner = Joiner.on(",");

            @Override
            public void onHeader(List<String> headers) {
                builder.add(commaJoiner.join(headers));
            }
            @Override
            public void onObjectValues(List<String> values) {
                builder.add(commaJoiner.join(values));
            }
        });
        return Joiner.on("\r\n").join( builder.build() );
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
