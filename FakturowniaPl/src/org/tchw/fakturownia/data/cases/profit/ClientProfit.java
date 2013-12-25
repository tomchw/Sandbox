package org.tchw.fakturownia.data.cases.profit;

import org.tchw.fakturownia.data.model.Client;

import com.google.common.collect.ImmutableList;

public class ClientProfit extends Profit<Client> {

    public final ImmutableList<InvoiceProfit> invoicesProfits;

    private ClientProfit(Client profitObject, ImmutableList<InvoiceProfit> invoicesProfits) {
        super(profitObject, Profit.sum(invoicesProfits));
        this.invoicesProfits = invoicesProfits;
    }

    public static Builder builder(Client client) {
        return new Builder(client);
    }

    public static class Builder {

        private final Client client;

        private final ImmutableList.Builder<InvoiceProfit> builder = ImmutableList.builder();

        public Builder(Client client) {
            this.client = client;
        }

        public Builder add(InvoiceProfit invoiceProfit) {
            builder.add(invoiceProfit);
            return this;
        }

        public ClientProfit build() {
            return new ClientProfit(client, builder.build());
        }
    }
}
