package org.tchw.fakturownia.api.model;

import org.json.JSONException;
import org.json.JSONObject;

public class InvoicePosition {

    private final JSONObject json;

    public InvoicePosition(JSONObject json) {
        this.json = json;
    }

    public String priceNet() {
        try {
            return json.getString("price_net");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
