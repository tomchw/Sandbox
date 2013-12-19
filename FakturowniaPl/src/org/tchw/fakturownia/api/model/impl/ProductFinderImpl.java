package org.tchw.fakturownia.api.model.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.tchw.data.json.Json;
import org.tchw.data.json.JsonArray;
import org.tchw.data.json.JsonObject;
import org.tchw.data.stream.Stream;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ProductFinderImpl implements ProductFinder {

    private final ImmutableMap<String, Product> products;

    public static ProductFinder loadFrom(Reader reader) {
        ImmutableMap.Builder<String, Product> builder = ImmutableMap.builder();
        appendReader(reader, builder);
        return new ProductFinderImpl(builder.build());
    }

    private static void appendReader(Reader reader, ImmutableMap.Builder<String, Product> builder) {
        JsonArray jsonArray = Json.takeFromReader().pass(Stream.toBufferedReader(reader)).asJsonArray().get();
        appendJsonArrayToBuilder(builder, jsonArray);
    }

    public static ProductFinder loadFromFiles() {
        return loadFrom(Stream.fromFile("firma-ksiegarska-werbum.products.json.1.txt").asBufferedReader().get());
    }

    private static void appendJsonArrayToBuilder(ImmutableMap.Builder<String, Product> builder, JsonArray jsonArray) {
        for (JsonObject jsonObject : jsonArray.getObjects()) {
            Product product = Product.create(jsonObject);
            builder.put(product.id(), product);
        }
    }

    private ProductFinderImpl(ImmutableMap<String, Product> products) {
        this.products = products;
    }

    @Override
    public Product byId(String id) {
        Preconditions.checkArgument(products.containsKey(id), "There is no product with id " + id);
        return products.get(id);
    }

    public static class Builder {

        private final ImmutableList.Builder<Reader> readers = ImmutableList.builder();

        public Builder addFile(String filePath) {
            try {
                add(new InputStreamReader(new FileInputStream(filePath)));
                return this;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Builder add(Reader reader) {
            readers.add(reader);
            return this;
        }

        public ProductFinder build() {
            ImmutableMap.Builder<String, Product> builder = ImmutableMap.builder();
            for (Reader reader : readers.build()) {
                appendReader(new BufferedReader(reader), builder);
            }
            return new ProductFinderImpl(builder.build());
        }
    }
}
