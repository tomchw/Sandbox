package org.tchw.fakturownia.data.model;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;
import org.tchw.fakturownia.data.model.impl.ClientFinderImpl;
import org.tchw.fakturownia.data.model.impl.InvoiceFinderImpl;
import org.tchw.fakturownia.data.model.impl.ProductFinderImpl;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.generic.stream.stream.Streams;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

public class Repository {

    private static final Logger log = Logger.getLogger(Repository.class);

    public final ClientFinder clients;
    public final InvoiceFinder invoices;
    public final ProductFinder products;

    public static Repository useRepositoryDirectory(RepositoryDirectory directory) {
        return fromDirectory(directory.repositoryDirectory().getPath());
    }

    private static Repository fromDirectory(String directory) {
        return builder()
            .clientsFromDirectory( Joiner.on("/").join(directory, "clients") )
            .invoicesFromDirectory( Joiner.on("/").join(directory, "invoices") )
            .productsFromDirectory( Joiner.on("/").join(directory, "products") )
            .build();
    }

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
            log.debug("Reading invoices from " + directoryPath);
            invoiceFinder = Streams.from(new File(directoryPath), Pattern.compile(".*?")).passTo(InvoiceFinderImpl.takeFromReaders());
            return this;
        }

        public Builder productsFromDirectory(String directoryPath) {
            Preconditions.checkState(productFinder == null, "ProductFinder is already set");
            log.debug("Reading products from " + directoryPath);
            productFinder = Streams.from(new File(directoryPath), Pattern.compile(".*?")).passTo(ProductFinderImpl.takeFromReaders());
            return this;
        }

        public Builder clientsFromDirectory(String directoryPath) {
            Preconditions.checkState(clientFinder == null, "ClientFinder is already set");
            log.debug("Reading clients from " + directoryPath);
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
