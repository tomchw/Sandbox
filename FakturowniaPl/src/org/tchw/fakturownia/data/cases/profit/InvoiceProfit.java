package org.tchw.fakturownia.data.cases.profit;

import org.tchw.fakturownia.data.model.Invoice;

import com.google.common.collect.ImmutableList;

public class InvoiceProfit extends Profit<Invoice> {

    public final ImmutableList<InvoicePositionProfit> invoicePositionsProfits;

    private InvoiceProfit(Invoice profitObject, ImmutableList<InvoicePositionProfit> invoicePositionsProfits) {
        super(profitObject, Profit.sum(invoicePositionsProfits));
        this.invoicePositionsProfits = invoicePositionsProfits;
    }

    public static Builder builder(Invoice invoice) {
        return new Builder(invoice);
    }

    public static class Builder {

        private final Invoice invoice;

        private final ImmutableList.Builder<InvoicePositionProfit> builder = ImmutableList.builder();

        public Builder(Invoice invoice) {
            this.invoice = invoice;
        }

        public Builder add(InvoicePositionProfit invoicePositionProfit) {
            builder.add(invoicePositionProfit);
            return this;
        }

        public InvoiceProfit build() {
            return new InvoiceProfit(invoice, builder.build());
        }
    }
}
