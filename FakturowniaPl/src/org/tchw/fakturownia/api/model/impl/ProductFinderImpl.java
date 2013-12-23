package org.tchw.fakturownia.api.model.impl;

import org.tchw.data.model.AbstractFinder;
import org.tchw.fakturownia.api.model.Product;
import org.tchw.fakturownia.api.model.ProductFinder;

import com.google.common.collect.ImmutableMap;

public class ProductFinderImpl extends AbstractFinder<Product> implements ProductFinder {

    public ProductFinderImpl(ImmutableMap<String, Product> items) {
        super(items);
    }

}
