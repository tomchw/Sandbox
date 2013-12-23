package org.tchw.fakturownia.api.model;

import org.tchw.data.stream.Stream;
import org.tchw.fakturownia.api.model.impl.ClientFinderImpl;
import org.tchw.fakturownia.api.model.impl.InvoiceFinderImpl;
import org.tchw.fakturownia.api.model.impl.ProductFinderImpl;

public class Repository {

    public final ClientFinder clients;
    public final InvoiceFinder invoices;
    public final ProductFinder products;

    public Repository(ClientFinder clients, InvoiceFinder invoices, ProductFinder products) {
        this.clients = clients;
        this.invoices = invoices;
        this.products = products;
    }

    public static Repository forTest(Class<?> testClass) {
        ClientFinder clientFinder = Stream.fromResource(testClass, "clients.txt").passTo(ClientFinderImpl.takeFromReader());
        InvoiceFinder invoiceFinder = Stream.fromResource(testClass, "invoices.txt").passTo(InvoiceFinderImpl.takeFromReader());
        ProductFinder productFinder = Stream.fromResource(testClass, "products.txt").passTo(ProductFinderImpl.takeFromReader());
        return new Repository(clientFinder, invoiceFinder, productFinder);
    }
}
