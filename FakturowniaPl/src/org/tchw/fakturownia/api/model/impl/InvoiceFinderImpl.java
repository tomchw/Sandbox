package org.tchw.fakturownia.api.model.impl;

import java.io.Reader;

import org.tchw.data.json.JsonLoader;
import org.tchw.data.model.AbstractFinder;
import org.tchw.data.stream.Stream;
import org.tchw.data.stream.Stream.From.ReaderPasser;
import org.tchw.fakturownia.api.model.Invoice;
import org.tchw.fakturownia.api.model.InvoiceFinder;

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
                ImmutableMap<String, Invoice> map = JsonLoader.create(Invoice.class).add(reader).build(Invoice.fromJson);
                return new InvoiceFinderImpl(map);
            }

        };
    }
}
