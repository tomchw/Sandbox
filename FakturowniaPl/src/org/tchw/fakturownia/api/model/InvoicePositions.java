package org.tchw.fakturownia.api.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class InvoicePositions {

    private final List<InvoicePosition> positions;

    public InvoicePositions(JSONArray json) {
        positions = invoicePositions(json);
    }

    private static List<InvoicePosition> invoicePositions(JSONArray json) {
        try {
            Builder<InvoicePosition> builder = ImmutableList.builder();
            for (int i = 0; i < json.length(); i++) {
                builder.add(new InvoicePosition(json.getJSONObject(i)));
            }
            return builder.build();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoicePosition> positions() {
        return positions;
    }

}
