package org.tchw.data.json;

import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.tchw.data.stream.Stream;

public class Json {

    public static Stream.From.ReaderPasser<From> takeFromReader() {
        return new Stream.From.ReaderPasser<From>() {
            @Override
            public From pass(Reader reader) {
                return new From(reader);
            }
        };
    }

    public static class From {

        private final Reader reader;

        public From(Reader reader) {
            this.reader = reader;
        }

        public Execution handle(JSONArrayHandling jsonArrayHandling) {
            return new Execution(jsonArrayHandling);
        }

        public interface JSONTokenerPasser<T> {
            T pass(JSONTokener tokener);
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

        public interface JsonArrayPasser<T> {
            T pass(JsonArray tokener);
        }

        public AsJsonArray asJsonArray() {
            return new AsJsonArray();
        }

        public class AsJsonArray {

            public JsonArray get() {
                return JsonArray.create(reader);
            }

            public <T> T passTo(JsonArrayPasser<T> passer) {
                return passer.pass(get());
            }
        }

        public interface JSONObjectPasser<T> {
            T pass(JSONObject jsonObject);
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

    private static JSONArray asJSONArray0(Reader reader) {
        try {
            return new JSONArray(new JSONTokener(reader));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
