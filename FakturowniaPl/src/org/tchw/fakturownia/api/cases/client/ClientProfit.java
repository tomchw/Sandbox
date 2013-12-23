package org.tchw.fakturownia.api.cases.client;

import org.tchw.fakturownia.api.model.Client;
import org.tchw.fakturownia.api.model.Invoice;

import com.google.common.collect.ImmutableList;

public class ClientProfit extends Profit<Client> {

    public final ImmutableList<Profit<Invoice>> invoicesProfits;

    private ClientProfit(Client profitObject, ImmutableList<Profit<Invoice>> invoicesProfits) {
        super(profitObject, Profit.sum(invoicesProfits));
        this.invoicesProfits = invoicesProfits;
    }

    public static Builder builder(Client client) {
        return new Builder(client);
    }

    public static class Builder {

        private final Client client;

        private final ImmutableList.Builder<Profit<Invoice>> builder = ImmutableList.builder();

        public Builder(Client client) {
            this.client = client;
        }

        public Builder add(Profit<Invoice> invoiceProfit) {
            builder.add(invoiceProfit);
            return this;
        }

        public ClientProfit build() {
            return new ClientProfit(client, builder.build());
        }
    }
}
