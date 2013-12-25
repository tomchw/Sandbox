package org.tchw.fakturownia.data.model;

import java.math.BigDecimal;

import org.tchw.generic.stream.json.JsonObject;

public class InvoicePosition {

    private final JsonObject json;

    public InvoicePosition(JsonObject json) {
        this.json = json;
    }

    public BigDecimal priceNet() {
        return json.getBigDecimal("price_net");
    }

    public String productId() {
        return json.getString("product_id");
    }

    public BigDecimal quantity() {
        return json.getBigDecimal("quantity");
    }

    public String name() {
        return json.getString("name");
    }
}
