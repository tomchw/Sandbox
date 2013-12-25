package org.tchw.data.json;

import java.io.Reader;

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

        public interface JSONTokenerPasser<T> {
            T pass(JSONTokener tokener);
        }

        public JSONTokener asJSONTokener() {
            return new JSONTokener(reader);
        }

        public <T> T passTo(JSONTokenerPasser<T> passer) {
            return passer.pass(asJSONTokener());
        }

        public interface JsonArrayPasser<T> {
            T pass(JsonArray tokener);
        }

        public JsonArray asJsonArray() {
            return JsonArray.create(reader);
        }

        public <T> T passTo(JsonArrayPasser<T> passer) {
            return passer.pass(asJsonArray());
        }

        public interface JsonObjectPasser<T> {
            T pass(JsonObject jsonObject);
        }

        public JsonObject asJsonObject() {
            try {
                return JsonObject.create( new JSONObject( new JSONTokener(reader) ));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        public <T> T passTo(JsonObjectPasser<T> passer) {
            return passer.pass(asJsonObject());
        }

    }

}
