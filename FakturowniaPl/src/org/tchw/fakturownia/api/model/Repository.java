package org.tchw.fakturownia.api.model;

import java.io.File;
import java.util.regex.Pattern;

import org.tchw.data.stream.Stream;
import org.tchw.data.stream.Streams;
import org.tchw.fakturownia.api.model.impl.ClientFinderImpl;
import org.tchw.fakturownia.api.model.impl.InvoiceFinderImpl;
import org.tchw.fakturownia.api.model.impl.ProductFinderImpl;

import com.google.common.base.Preconditions;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ClientFinder clientFinder;
        private InvoiceFinder invoiceFinder;
        private ProductFinder productFinder;

        public Builder invoicesFromDirectory(String directoryPath) {
            Preconditions.checkState(invoiceFinder == null, "InvoiceFinder is already set");
            invoiceFinder = Streams.from(new File(directoryPath), Pattern.compile(".*?")).passTo(InvoiceFinderImpl.takeFromReaders());
            return this;
        }

        public Builder productsFromDirectory(String directoryPath) {
            Preconditions.checkState(productFinder == null, "ProductFinder is already set");
            productFinder = Streams.from(new File(directoryPath), Pattern.compile(".*?")).passTo(ProductFinderImpl.takeFromReaders());
            return this;
        }

        public Builder clientsFromDirectory(String directoryPath) {
            Preconditions.checkState(clientFinder == null, "ClientFinder is already set");
            clientFinder = Streams.from(new File(directoryPath), Pattern.compile(".*?")).passTo(ClientFinderImpl.takeFromReaders());
            return this;
        }

        public Repository build() {
            Preconditions.checkNotNull(clientFinder);
            Preconditions.checkNotNull(invoiceFinder);
            Preconditions.checkNotNull(productFinder);
            return new Repository(clientFinder, invoiceFinder, productFinder);
        }
    }
}
