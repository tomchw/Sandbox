package org.tchw.fakturownia.api.model.impl;

import java.io.Reader;

import org.tchw.data.json.JsonLoader;
import org.tchw.data.model.AbstractFinder;
import org.tchw.data.stream.Stream;
import org.tchw.data.stream.Stream.From.ReaderPasser;
import org.tchw.data.stream.Streams.From.ReadersPasser;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ProductFinderImpl extends AbstractFinder<Product> implements ProductFinder {

    public ProductFinderImpl(ImmutableMap<String, Product> items) {
        super(items);
    }

    public static ReaderPasser<ProductFinder> takeFromReader() {
        return new Stream.From.ReaderPasser<ProductFinder>() {

            @Override
            public ProductFinder pass(Reader reader) {
                ImmutableMap<String, Product> map = JsonLoader.create(Product.class).add(reader).build(Product.fromJson);
                return new ProductFinderImpl(map);
            }

        };
    }

    public static ReadersPasser<ProductFinder> takeFromReaders() {
        return new ReadersPasser<ProductFinder>() {
            @Override
            public ProductFinder pass(ImmutableList<? extends Reader> readers) {
                ImmutableMap<String, Product> map = JsonLoader.create(Product.class).addAll(readers).build(Product.fromJson);
                return new ProductFinderImpl(map);
            }
        };
    }
}
