package org.tchw.fakturownia.services.calculateClientsProfits;

import java.io.IOException;
import java.math.BigDecimal;

import org.supercsv.io.CsvListWriter;
import org.tchw.fakturownia.model.Client;
import org.tchw.fakturownia.model.Invoice;
import org.tchw.fakturownia.model.InvoicePosition;
import org.tchw.fakturownia.model.Product;

import com.google.common.collect.ImmutableList;

public class ClientsProfitsToExcelCsv implements AllClientsProfitCalculator.Handling {

    private final CsvListWriter csvWriter;

    public ClientsProfitsToExcelCsv(CsvListWriter writer) {
        csvWriter = writer;
        writeHeader();
    }

    private void writeHeader() {
        try {
            csvWriter.writeHeader(
                    "Client", "Full Client Profit",
                    "Invoice number", "Full invoice profit",
                    "Product", "Full Product Profit", "Price net", "Purchase net", "Single Product Profit", "Quantity");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClientProfit(ClientProfit clientProfit) {
        Client client = clientProfit.profitObject();
        writeToCsv(ImmutableList.of(client.name(), clientProfit.profitValue()));
        doInvoicesProfits(clientProfit.invoicesProfits, ImmutableList.of(client.name(), ""));
    }

    private void doInvoicesProfits(ImmutableList<InvoiceProfit> invoicesProfits, ImmutableList<? extends Object> clientList) {
        for (InvoiceProfit invoiceProfit : invoicesProfits) {
            Invoice invoice = invoiceProfit.profitObject();
            writeToCsv(ImmutableList.builder().addAll(clientList).add(invoice.number()).add(invoiceProfit.profitValue()).build());
            ImmutableList<Object> list = ImmutableList.builder().addAll(clientList).addAll(ImmutableList.of(invoice.number(), "")).build();
            doInvoicePositionsProfits(invoiceProfit.invoicePositionsProfits, list);
        }
    }

    private void doInvoicePositionsProfits(ImmutableList<InvoicePositionProfit> invoicePositionsProfits, ImmutableList<Object> list) {
        for (InvoicePositionProfit invoicePositionProfit : invoicePositionsProfits) {
            Product product = invoicePositionProfit.product;
            InvoicePosition invoicePosition = invoicePositionProfit.profitObject();
            BigDecimal singlePositionProfit = invoicePositionProfit.singleProductProfit;
            BigDecimal fullPositionProfit = invoicePositionProfit.profitValue();

            ImmutableList<? extends Object> savePositionsLine = ImmutableList.of(
                    product.name(),
                    fullPositionProfit,
                    invoicePosition.priceNet(),
                    product.purchasePriceNet(),
                    singlePositionProfit,
                    invoicePosition.quantity()
                    );

            ImmutableList<Object> wholeLineList = ImmutableList.builder().addAll(list).addAll(savePositionsLine).build();
            writeToCsv(wholeLineList);
        }
    }

    private void writeToCsv(ImmutableList<? extends Object> wholeLineList) {
        ImmutableList.Builder<Object> builder = ImmutableList.builder();
        for (Object object : wholeLineList) {
            if( object instanceof BigDecimal ) {
                builder.add(((BigDecimal) object).toPlainString().replace(".", ","));
            } else {
                builder.add(object);
            }
        }
        try {
            csvWriter.write(builder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
