package org.tchw.fakturownia.products;

import java.util.Iterator;

import org.junit.Test;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;
import org.tchw.fakturownia.api.model.impl.ProductFinderImpl;
import org.tchw.generic.stream.stream.Streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ProductFinderTest {

    @Test
    public void test() {
        ProductFinder productFinder = Streams.fromResources(getClass(), "products1.txt", "products2.txt").passTo(ProductFinderImpl.takeFromReaders());
        Iterator<Product> products = productFinder.all().iterator();
        assertEquals("1", products.next().id());
        assertEquals("B", products.next().name());
        assertEquals("3", products.next().id());
        assertEquals("40.0", products.next().purchasePriceNet());
        assertFalse(products.hasNext());
    }

}
