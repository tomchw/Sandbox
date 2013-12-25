package org.tchw.fakturownia.data.model;

import org.json.JSONObject;
import org.tchw.generic.stream.json.JsonObject;
import org.tchw.generic.stream.json.JsonToPojo.JsonObjectTo;

public class Client {

    private final JsonObject json;

    public static final JsonObjectTo<Client> fromJson =
        new JsonObjectTo<Client>() {
            @Override
            public Client create(JsonObject json) {
                return Client.create(json);
            }
        };

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

    public String name() {
        return json.getString("name");
    }

}
