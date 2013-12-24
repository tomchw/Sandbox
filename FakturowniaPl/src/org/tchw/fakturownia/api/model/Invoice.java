package org.tchw.fakturownia.api.model;

import java.util.List;

import org.json.JSONObject;
import org.tchw.data.json.Json.From.JsonObjectPasser;
import org.tchw.data.json.JsonArray;
import org.tchw.data.json.JsonLoader.JsonObjectTo;
import org.tchw.data.json.JsonObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class Invoice {

    private final JsonObject json;

    private final List<InvoicePosition> positions;

    public static final JsonObjectTo<Invoice> fromJson =
            new JsonObjectTo<Invoice>() {
                @Override
                public Invoice create(JsonObject json) {
                    return Invoice.create(json);
                }
            };

    public static Invoice create(JsonObject json) {
        return new Invoice(json);
    }

    public static Invoice create(JSONObject json) {
        return create(JsonObject.create(json));
    }

    private Invoice(JsonObject jsonObject) {
        this.json = jsonObject;
        this.positions = invoicePositions(this.json.getArray("positions"));
    }

    public String id() {
        return json.getString("id");
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

    public static JsonObjectPasser<From> takeFromJsonObject() {
        return new JsonObjectPasser<From>() {
            @Override
            public From pass(JsonObject jsonObject) {
                return new From(jsonObject);
            }
        };
    }

    public static class From {

        private final JsonObject jsonObject;

        public From(JsonObject jsonObject) {
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
                return Invoice.create(jsonObject);
            }

            public <T> T passTo(InvoicePasser<T> passer) {
                return passer.pass(get());
            }
        }
    }

}
