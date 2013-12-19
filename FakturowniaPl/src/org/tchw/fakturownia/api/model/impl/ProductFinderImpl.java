package org.tchw.fakturownia.api.model.impl;

import org.tchw.data.json.Json;
import org.tchw.data.stream.Stream;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

public class ProductFinderImpl implements ProductFinder {

    private final ImmutableMap<String, Product> products;

    public static ProductFinder loadFromFiles() {
        Stream.fromFile("firma-ksiegarska-werbum.products.json.1.txt").asBufferedReader().passTo(Json.takeFromReader()).asJSONArray().get();
        throw new RuntimeException("Not done yet");
    }

    private ProductFinderImpl(ImmutableMap<String, Product> products) {
        this.products = products;
    }

    @Override
    public Product byId(String id) {
        Preconditions.checkArgument(products.containsKey(id), "There is no product with id " + id);
        return products.get(id);
    }

}
