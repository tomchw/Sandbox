package org.tchw.fakturownia.model.invoices.finder;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.fakturownia.model.Invoice;
import org.tchw.fakturownia.model.InvoiceFinder;
import org.tchw.fakturownia.model.impl.InvoiceFinderImpl;
import org.tchw.generic.stream.stream.Streams;

import com.google.common.collect.UnmodifiableIterator;

public class InvoiceFinderTest {

    @Test
    public void test() {
        InvoiceFinder invoiceFinder = Streams.fromResources(getClass(), "singleInvoice.1.json", "singleInvoice.2.json").passTo(InvoiceFinderImpl.takeFromReaders());
        UnmodifiableIterator<Invoice> invoiceIterator = invoiceFinder.all().iterator();
        Assert.assertEquals("30001", invoiceIterator.next().id());
        Assert.assertEquals("20002", invoiceIterator.next().positions().get(1).productId());
        Assert.assertFalse(invoiceIterator.hasNext());
    }

}
