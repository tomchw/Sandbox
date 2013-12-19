package org.tchw.fakturownia.api.model;

import org.json.JSONObject;
import org.tchw.data.json.JsonObject;

public class Client {

    private final JsonObject json;

    public static Client create(JsonObject json) {
        return new Client(json);
    }

    public static Client create(JSONObject json) {
        return new Client(JsonObject.create(json));
    }

    private Client(JsonObject json) {
        this.json = json;
    }

    public String id() {
        return json.getString("id");
    }

}
