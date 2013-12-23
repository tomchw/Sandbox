package org.tchw.fakturownia.api.model;

import java.util.List;

import org.json.JSONObject;
import org.tchw.data.json.Json.From.JSONObjectPasser;
import org.tchw.data.json.JsonArray;
import org.tchw.data.json.JsonObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class Invoice {

    private final JsonObject json;

    private final List<InvoicePosition> positions;

    public Invoice(JSONObject jsonObject) {
        this.json = JsonObject.create(jsonObject);
        this.positions = invoicePositions(this.json.getArray("positions"));
    }

    public String clientId() {
        return json.getString("client_id");
    }

    public String number() {
        return json.getString("number");
    }

    public List<InvoicePosition> positions() {
        return positions;
    }

    private static List<InvoicePosition> invoicePositions(JsonArray json) {
        Builder<InvoicePosition> builder = ImmutableList.builder();
        for (int i = 0; i < json.length(); i++) {
            builder.add(new InvoicePosition(json.getObject(i)));
        }
        return builder.build();
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
