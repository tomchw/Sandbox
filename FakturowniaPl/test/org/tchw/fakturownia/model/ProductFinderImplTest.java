package org.tchw.fakturownia.model;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.stream.Stream;
import org.tchw.fakturownia.api.model.ProductFinder;
import org.tchw.fakturownia.api.model.impl.ProductFinderImpl;

public class ProductFinderImplTest {

    @Test
    public void test() {
        ProductFinder productFinder = ProductFinderImpl.loadFrom(Stream.fromResource(ProductFinderImplTest.class, "products.txt").asBufferedReader().get());
        Assert.assertEquals("1", productFinder.byId("1").id());
        Assert.assertEquals("20.0", productFinder.byId("2").purchasePriceNet());
    }

}
