package org.tchw.fakturownia.api.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tchw.data.json.Json.From.JSONObjectPasser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class Invoice {

    private final JSONObject json;

    private final List<InvoicePosition> positions;

    public Invoice(JSONObject jsonObject) {
        this.json = jsonObject;
        try {
            this.positions = invoicePositions(jsonObject.getJSONArray("positions"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int clientId() {
        try {
            return json.getInt("client_id");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String number() {
        try {
            return json.getString("number");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoicePosition> positions() {
        return positions;
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

    public static JSONObjectPasser<From> takeFromJSONObject() {
        return new JSONObjectPasser<From>() {
            @Override
            public From pass(JSONObject jsonObject) {
                return new From(jsonObject);
            }
        };
    }

    public static class From {

        private final JSONObject jsonObject;

        public From(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public interface InvoicePasser<T> {
            T pass(Invoice invoice);
        }

        public AsInvoice asInvoice() {
            return new AsInvoice();
        }

        public class AsInvoice {

            public Invoice get() {
                return new Invoice( jsonObject );
            }

            public <T> T passTo(InvoicePasser<T> passer) {
                return passer.pass(get());
            }
        }
    }

}
