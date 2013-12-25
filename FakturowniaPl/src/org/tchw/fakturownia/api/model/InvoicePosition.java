package org.tchw.fakturownia.api.model;

import org.tchw.generic.stream.json.JsonObject;

public class InvoicePosition {

    private final JsonObject json;

    public InvoicePosition(JsonObject json) {
        this.json = json;
    }

    public String priceNet() {
        return json.getString("price_net");
    }

    public String productId() {
        return json.getString("product_id");
    }

    public String quantity() {
        return json.getString("quantity");
    }

    public String name() {
        return json.getString("name");
    }
}
