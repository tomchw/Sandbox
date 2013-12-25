package org.tchw.fakturownia.data.cases.profit;

import java.math.BigDecimal;

import org.tchw.fakturownia.data.model.Client;
import org.tchw.fakturownia.data.model.Invoice;
import org.tchw.fakturownia.data.model.InvoicePosition;
import org.tchw.fakturownia.data.model.Product;
import org.tchw.fakturownia.data.model.Repository;

public class ClientProfitCalculator {

    private final Repository repository;

    public ClientProfitCalculator(Repository repository) {
        this.repository = repository;
    }

    public ClientProfit calculate(Client client) {
        ClientProfit.Builder clientProfitBuilder = ClientProfit.builder(client);
        for (Invoice invoice : repository.invoices.byClientId(client.id())) {
            clientProfitBuilder.add(calculateInvoiceProfit(invoice));
        }
        return clientProfitBuilder.build();
    }

    private InvoiceProfit calculateInvoiceProfit(Invoice invoice) {
        InvoiceProfit.Builder invoiceProfitBuilder = InvoiceProfit.builder(invoice);
        for (InvoicePosition invoicePosition : invoice.positions()) {
            invoiceProfitBuilder.add(calculateInvoicePositionProfit(invoicePosition));
        }
        return invoiceProfitBuilder.build();
    }

    private InvoicePositionProfit calculateInvoicePositionProfit(InvoicePosition invoicePosition) {
        BigDecimal invoicePositionQuantity = new BigDecimal(invoicePosition.quantity());
        BigDecimal invoicePorisitionpriceNet = new BigDecimal(invoicePosition.priceNet());

        Product product = repository.products.byId(invoicePosition.productId());
        BigDecimal purchasePriceNet = new BigDecimal(product.purchasePriceNet());

        BigDecimal singleProductProfit = invoicePorisitionpriceNet.add(purchasePriceNet.negate());
        BigDecimal profit = singleProductProfit.multiply(invoicePositionQuantity);
        return new InvoicePositionProfit(invoicePosition, profit, product, singleProductProfit);
    }
}
