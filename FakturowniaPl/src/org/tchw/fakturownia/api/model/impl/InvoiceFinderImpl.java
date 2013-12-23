package org.tchw.fakturownia.api.model.impl;

import org.tchw.data.model.AbstractFinder;
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
}
