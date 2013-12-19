package org.tchw.fakturownia.api.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private final JSONObject jsonObject;

    public Product(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String priceNet() {
        try {
            return jsonObject.getString("price_net");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
