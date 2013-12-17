package org.tchw.data.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class Json {

    public static From from(InputStream inputStream) {
        return new From(inputStream);
    }

    public static From from(File file) {
        try {
            return new From(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static From fromFile(String filePath) {
        try {
            return new From(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class From {

        private final BufferedReader reader;

        public From(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public Execution handle(JSONArrayHandling jsonArrayHandling) {
            return new Execution(jsonArrayHandling);
        }

        public <T> T passTo(Passer<T> passer) {
            return passer.pass(reader);
        }

        public interface Passer<T> {
            T pass(BufferedReader reader);
        }

        public class Execution {

            private final JSONArrayHandling jsonArrayHandling;

            public Execution(JSONArrayHandling jsonArrayHandling) {
                this.jsonArrayHandling = jsonArrayHandling;
            }

            public void executeSync() {
                jsonArrayHandling.handle(asJSONArray(reader));
            }
        }
    }

    private static JSONArray asJSONArray(BufferedReader reader) {
        try {
            return new JSONArray(new JSONTokener(reader));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
