package org.tchw.data.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public static From fromResource(Class<?> clazz, String resourceName) {
        return new From(clazz.getResourceAsStream(resourceName));
    }

    public static class From {

        private final BufferedReader reader;

        public From(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public Execution handle(JSONArrayHandling jsonArrayHandling) {
            return new Execution(jsonArrayHandling);
        }

        public AsJSONTokener asJSONTokener() {
            return new AsJSONTokener();
        }

        public class AsJSONTokener {

            public JSONTokener get() {
                return new JSONTokener(reader);
            }

            public <T> T passTo(JSONTokenerPasser<T> passer) {
                return passer.pass(get());
            }
        }

        public AsJSONArray asJSONArray() {
            return new AsJSONArray();
        }

        public class AsJSONArray {

            public JSONArray get() {
                try {
                    return new JSONArray( new JSONTokener(reader) );
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T passTo(JSONArrayPasser<T> passer) {
                return passer.pass(get());
            }
        }

        public AsJSONObject asJSONObject() {
            return new AsJSONObject();
        }

        public class AsJSONObject {

            public JSONObject get() {
                try {
                    return new JSONObject( new JSONTokener(reader) );
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T passTo(JSONObjectPasser<T> passer) {
                return passer.pass(get());
            }
        }

        public interface JSONTokenerPasser<T> {
            T pass(JSONTokener tokener);
        }

        public interface JSONArrayPasser<T> {
            T pass(JSONArray tokener);
        }

        public interface JSONObjectPasser<T> {
            T pass(JSONObject tokener);
        }

        public class Execution {

            private final JSONArrayHandling jsonArrayHandling;

            public Execution(JSONArrayHandling jsonArrayHandling) {
                this.jsonArrayHandling = jsonArrayHandling;
            }

            public void executeSync() {
                jsonArrayHandling.handle(asJSONArray0(reader));
            }
        }
    }

    private static JSONArray asJSONArray0(BufferedReader reader) {
        try {
            return new JSONArray(new JSONTokener(reader));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
