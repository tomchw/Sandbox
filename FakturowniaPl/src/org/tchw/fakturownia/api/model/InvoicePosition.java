package org.tchw.fakturownia.api.model;

import org.tchw.data.json.JsonObject;

public class InvoicePosition {

    private final JsonObject json;

    public InvoicePosition(JsonObject json) {
        this.json = json;
    }

    public String priceNet() {
        return json.getString("price_net");
    }
}
