package org.tchw.fakturownia.api.cases.client;

import org.tchw.fakturownia.api.model.Client;
import org.tchw.fakturownia.api.model.Invoice;
import org.tchw.fakturownia.api.model.InvoiceFinder;
import org.tchw.fakturownia.api.model.InvoicePosition;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;

public class ClientStats {

    private final Client client;

    private final ProductFinder productFinder;

    private final InvoiceFinder invoiceFinder;

    public ClientStats(Client client, ProductFinder productFinder, InvoiceFinder invoiceFinder) {
        this.client = client;
        this.productFinder = productFinder;
        this.invoiceFinder = invoiceFinder;
    }

    public void execute() {
        for (Invoice invoice : invoiceFinder.byClientId(client.id())) {
            handleInvoice(invoice);
        }
    }

    private void handleInvoice(Invoice invoice) {
        for (InvoicePosition invoicePosition : invoice.positions()) {
            String productId = invoicePosition.productId();
            String quantity = invoicePosition.quantity();
            String priceNet = invoicePosition.priceNet();

            Product product = productFinder.byId(productId);
            String purchasePriceNet = product.purchasePriceNet();
        }
    }

}
