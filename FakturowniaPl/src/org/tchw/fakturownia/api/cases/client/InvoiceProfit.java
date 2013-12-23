package org.tchw.fakturownia.api.cases.client;

import org.tchw.fakturownia.api.model.Invoice;
import org.tchw.fakturownia.api.model.InvoicePosition;

import com.google.common.collect.ImmutableList;

public class InvoiceProfit extends Profit<Invoice> {

    public final ImmutableList<Profit<InvoicePosition>> invoicePositionsProfits;

    public InvoiceProfit(Invoice profitObject, ImmutableList<Profit<InvoicePosition>> invoicePositionsProfits) {
        super(profitObject, Profit.sum(invoicePositionsProfits));
        this.invoicePositionsProfits = invoicePositionsProfits;
    }

    public static class Builder {

        private final Invoice invoice;

        private final ImmutableList.Builder<Profit<InvoicePosition>> builder = ImmutableList.builder();

        public Builder(Invoice invoice) {
            this.invoice = invoice;
        }

        public Builder add(Profit<InvoicePosition> invoicePositionProfit) {
            builder.add(invoicePositionProfit);
            return this;
        }

        public InvoiceProfit build() {
            return new InvoiceProfit(invoice, builder.build());
        }
    }
}
