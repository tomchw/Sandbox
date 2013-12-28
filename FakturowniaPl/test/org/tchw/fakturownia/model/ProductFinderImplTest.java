package org.tchw.fakturownia.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.fakturownia.data.model.Product;
import org.tchw.fakturownia.data.model.ProductFinder;
import org.tchw.fakturownia.data.model.impl.ProductFinderImpl;
import org.tchw.generic.stream.json.JsonObject;
import org.tchw.generic.stream.json.JsonToPojo;
import org.tchw.generic.stream.json.JsonToPojo.JsonObjectTo;
import org.tchw.generic.stream.stream.Stream;

import com.google.common.collect.ImmutableMap;

public class ProductFinderImplTest {

    @Test
    public void test() {
        ImmutableMap<String, Product> productsAsMap = JsonToPojo.create().add(Stream.fromResource(ProductFinderImplTest.class, "products.txt").asBufferedReader()).buildAsMap(new JsonObjectTo<Product>() {
            @Override
            public Product create(JsonObject json) {
                return Product.create(json);
            }
        });
        ProductFinder productFinder = new ProductFinderImpl(productsAsMap);
        Assert.assertEquals("1", productFinder.byId("1").id());
        Assert.assertEquals(new BigDecimal("20.0"), productFinder.byId("2").purchasePriceNet());
    }

}
