package org.tchw.fakturownia.model.impl;

import java.io.Reader;

import org.tchw.fakturownia.model.Invoice;
import org.tchw.fakturownia.model.InvoiceFinder;
import org.tchw.generic.stream.json.JsonToPojo;
import org.tchw.generic.stream.model.AbstractFinder;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.generic.stream.stream.Streams;
import org.tchw.generic.stream.stream.Stream.From.ReaderPasser;
import org.tchw.generic.stream.stream.Streams.From.ReadersPasser;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class InvoiceFinderImpl extends AbstractFinder<Invoice> implements InvoiceFinder {

    public InvoiceFinderImpl(ImmutableMap<String, Invoice> items) {
        super(items);
    }

    @Override
    public ImmutableList<Invoice> byClientId(final String clientId) {
        return filter(new Predicate<Invoice>() {
            @Override
            public boolean apply(Invoice invoice) {
                return clientId.equals(invoice.clientId());
            }
        });
    }

    public static ReaderPasser<InvoiceFinder> takeFromReader() {
        return new Stream.From.ReaderPasser<InvoiceFinder>() {

            @Override
            public InvoiceFinder pass(Reader reader) {
                ImmutableMap<String, Invoice> map = JsonToPojo.create().add(reader).buildAsMap(Invoice.fromJson);
                return new InvoiceFinderImpl(map);
            }

        };
    }

    public static ReadersPasser<InvoiceFinder> takeFromReaders() {
        return new Streams.From.ReadersPasser<InvoiceFinder>() {
            @Override
            public InvoiceFinder pass(ImmutableList<? extends Reader> readers) {
                ImmutableMap<String, Invoice> map = JsonToPojo.create().addAll(readers).buildAsMap(Invoice.fromJson);
                return new InvoiceFinderImpl(map);
            }
        };
    }
}
