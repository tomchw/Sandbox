package org.tchw.fakturownia.model;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.fakturownia.data.model.Invoice;
import org.tchw.generic.stream.json.Json;
import org.tchw.generic.stream.stream.Stream;

public class InvoiceTest {

    @Test
    public void test() {
        Invoice invoice = Stream.fromResource(getClass(), "invoice.txt").passTo(Json.takeFromReader())
            .passTo(Invoice.takeFromJsonObject()).asInvoice().get();

        Assert.assertEquals("376848", invoice.clientId());
        Assert.assertEquals("2/09/2013", invoice.number());
        Assert.assertEquals("21.5", invoice.positions().get(0).priceNet());
    }

}
