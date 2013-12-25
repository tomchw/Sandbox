package org.tchw.fakturownia.model;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.json.JsonToPojo;
import org.tchw.data.json.JsonToPojo.JsonObjectTo;
import org.tchw.data.json.JsonObject;
import org.tchw.data.stream.Stream;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;
import org.tchw.fakturownia.api.model.impl.ProductFinderImpl;

import com.google.common.collect.ImmutableMap;

public class ProductFinderImplTest {

    @Test
    public void test() {
        ImmutableMap<String, Product> productsAsMap = JsonToPojo.create(Product.class).add(Stream.fromResource(ProductFinderImplTest.class, "products.txt").asBufferedReader()).build(new JsonObjectTo<Product>() {
            @Override
            public Product create(JsonObject json) {
                return Product.create(json);
            }
        });
        ProductFinder productFinder = new ProductFinderImpl(productsAsMap);
        Assert.assertEquals("1", productFinder.byId("1").id());
        Assert.assertEquals("20.0", productFinder.byId("2").purchasePriceNet());
    }

}
