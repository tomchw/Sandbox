package org.tchw.fakturownia.services.calculateClientsProfits;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.model.Client;
import org.tchw.fakturownia.model.Invoice;
import org.tchw.fakturownia.model.InvoicePosition;
import org.tchw.fakturownia.model.Product;
import org.tchw.fakturownia.model.Repository;

public class ClientProfitCalculator {

    private final Logger log = Logger.getLogger(getClass());

    private final Repository repository;

    public ClientProfitCalculator(Repository repository) {
        this.repository = repository;
    }

    public ClientProfit calculate(Client client) {
        log.debug("Calculating profit for client " + client.name());
        ClientProfit.Builder clientProfitBuilder = ClientProfit.builder(client);
        for (Invoice invoice : repository.invoices.byClientId(client.id())) {
            clientProfitBuilder.add(calculateInvoiceProfit(invoice));
        }
        return clientProfitBuilder.build();
    }

    private InvoiceProfit calculateInvoiceProfit(Invoice invoice) {
        log.debug("Calculating profit for invoice " + invoice.number());
        InvoiceProfit.Builder invoiceProfitBuilder = InvoiceProfit.builder(invoice);
        for (InvoicePosition invoicePosition : invoice.positions()) {
            try {
                invoiceProfitBuilder.add(calculateInvoicePositionProfit(invoicePosition));
            } catch (RuntimeException e) {
                log.warn("Cannot calculate profit for invoice " + invoice.number() + "(id:" + invoice.id() + ")" + " and inovice position " + invoicePosition.name(), e);
            }
        }
        return invoiceProfitBuilder.build();
    }

    private InvoicePositionProfit calculateInvoicePositionProfit(InvoicePosition invoicePosition) {
        BigDecimal invoicePositionQuantity = invoicePosition.quantity();
        BigDecimal invoicePorisitionpriceNet = invoicePosition.priceNet();

        Product product = repository.products.byId(invoicePosition.productId());
        BigDecimal purchasePriceNet = product.purchasePriceNet();

        BigDecimal singleProductProfit = invoicePorisitionpriceNet.add(purchasePriceNet.negate());
        BigDecimal profit = singleProductProfit.multiply(invoicePositionQuantity);
        return new InvoicePositionProfit(invoicePosition, profit, product, singleProductProfit);
    }
}
