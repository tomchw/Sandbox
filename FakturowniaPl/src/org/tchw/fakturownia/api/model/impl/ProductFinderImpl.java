package org.tchw.fakturownia.api.model.impl;

import java.io.Reader;

import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;
import org.tchw.generic.stream.json.JsonToPojo;
import org.tchw.generic.stream.model.AbstractFinder;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.generic.stream.stream.Stream.From.ReaderPasser;
import org.tchw.generic.stream.stream.Streams.From.ReadersPasser;

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
                ImmutableMap<String, Product> map = JsonToPojo.create().add(reader).buildAsMap(Product.fromJson);
                return new ProductFinderImpl(map);
            }

        };
    }

    public static ReadersPasser<ProductFinder> takeFromReaders() {
        return new ReadersPasser<ProductFinder>() {
            @Override
            public ProductFinder pass(ImmutableList<? extends Reader> readers) {
                ImmutableMap<String, Product> map = JsonToPojo.create().addAll(readers).buildAsMap(Product.fromJson);
                return new ProductFinderImpl(map);
            }
        };
    }
}
