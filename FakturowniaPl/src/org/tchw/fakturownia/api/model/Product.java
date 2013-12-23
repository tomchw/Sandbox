package org.tchw.fakturownia.api.model;

import org.json.JSONObject;
import org.tchw.data.json.JsonLoader.JsonObjectTo;
import org.tchw.data.json.JsonObject;

public class Product {

    private final JsonObject json;

    public static final JsonObjectTo<Product> fromJson =
            new JsonObjectTo<Product>() {
                @Override
                public Product create(JsonObject json) {
                    return Product.create(json);
                }
            };

    public static Product create(JsonObject json) {
        return new Product(json);
    }

    public static Product create(JSONObject json) {
        return new Product(JsonObject.create(json));
    }

    private Product(JsonObject json) {
        this.json = json;
    }

    public String id() {
        return json.getString("id");
    }

    public String name() {
        return json.getString("name");
    }

    public String purchasePriceNet() {
        return json.getString("purchase_price_net");
    }

}
