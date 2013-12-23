package org.tchw.fakturownia.api.cases.client;

import java.math.BigDecimal;

import org.tchw.fakturownia.api.model.Client;
import org.tchw.fakturownia.api.model.Invoice;
import org.tchw.fakturownia.api.model.InvoicePosition;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.Repository;

import com.google.common.collect.ImmutableList;

public class ClientProfitCalculator {

    private final Repository repository;

    public ClientProfitCalculator(Repository repository) {
        this.repository = repository;
    }

    public BigDecimal calculate(Client client) {
        ImmutableList<Invoice> clientInvoices = repository.invoices.byClientId(client.id());
        BigDecimal clientProfit = BigDecimal.ZERO;
        for (Invoice invoice : clientInvoices) {
            clientProfit = clientProfit.add(calculateInvoiceProfit(invoice));
        }
        return clientProfit.setScale(2);
    }

    private BigDecimal calculateInvoiceProfit(Invoice invoice) {
        BigDecimal invoiceProfit = BigDecimal.ZERO;
        for (InvoicePosition invoicePosition : invoice.positions()) {
            BigDecimal invoicePositionQuantity = new BigDecimal(invoicePosition.quantity());
            BigDecimal invoicePorisitionpriceNet = new BigDecimal(invoicePosition.priceNet());

            Product product = repository.products.byId(invoicePosition.productId());
            BigDecimal purchasePriceNet = new BigDecimal(product.purchasePriceNet());

            BigDecimal singleProductProfit = invoicePorisitionpriceNet.min(purchasePriceNet);
            BigDecimal invoicePositionProfit = singleProductProfit.multiply(invoicePositionQuantity);
            invoiceProfit = invoiceProfit.add(invoicePositionProfit);
        }
        return invoiceProfit;
    }

}
