package org.tchw.fakturownia.data.cases.profit;

import java.io.IOException;
import java.math.BigDecimal;

import org.supercsv.io.CsvListWriter;
import org.tchw.fakturownia.data.model.Client;
import org.tchw.fakturownia.data.model.Invoice;
import org.tchw.fakturownia.data.model.InvoicePosition;
import org.tchw.fakturownia.data.model.Product;

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
        ImmutableList<? extends Object> clientList = saveClientLine(client.name(), clientProfit.profitValue());
        doInvoicesProfits(clientProfit.invoicesProfits, clientList);
    }

    private void doInvoicesProfits(ImmutableList<InvoiceProfit> invoicesProfits, ImmutableList<? extends Object> clientList) {
        for (InvoiceProfit invoiceProfit : invoicesProfits) {
            Invoice invoice = invoiceProfit.profitObject();
            ImmutableList<? extends Object> invoiceList = saveInvoiceLine(invoice.number(), invoiceProfit.profitValue());
            ImmutableList<Object> list = ImmutableList.builder().addAll(clientList).addAll(invoiceList).build();
            doInvoicePositionsProfits(invoiceProfit.invoicePositionsProfits, list);
        }
    }

    private void doInvoicePositionsProfits(ImmutableList<InvoicePositionProfit> invoicePositionsProfits, ImmutableList<Object> list) {
        for (InvoicePositionProfit invoicePositionProfit : invoicePositionsProfits) {
            Product product = invoicePositionProfit.product;
            InvoicePosition invoicePosition = invoicePositionProfit.profitObject();
            BigDecimal singlePositionProfit = invoicePositionProfit.singleProductProfit;
            BigDecimal fullPositionProfit = invoicePositionProfit.profitValue();

            ImmutableList<? extends Object> savePositionsLine = savePositionsLine(
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

    private void writeToCsv(ImmutableList<Object> wholeLineList) {
        try {
            csvWriter.write(wholeLineList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImmutableList<? extends Object> savePositionsLine(Object...cells) {
        return ImmutableList.copyOf(cells);
    }

    private ImmutableList<? extends Object> saveInvoiceLine(String number, BigDecimal profitValue) {
        return ImmutableList.of(number, profitValue);
    }

    private ImmutableList<? extends Object> saveClientLine(String name, BigDecimal profitValue) {
        return ImmutableList.of(name, profitValue);
    }

}
