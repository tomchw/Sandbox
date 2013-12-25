package org.tchw.generic.data.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public abstract class AbstractFinder<T> implements Finder<T> {

    private final ImmutableMap<String, T> itemsAsMap;

    private final ImmutableList<T> itemsAsList;

    public AbstractFinder(ImmutableMap<String, T> items) {
        this.itemsAsMap = items;
        this.itemsAsList = ImmutableList.copyOf(items.values());
    }

    @Override
    public T byId(String id) {
        Preconditions.checkArgument(itemsAsMap.containsKey(id), "There is no item with id " + id);
        return itemsAsMap.get(id);
    }

    @Override
    public ImmutableList<T> all() {
        return itemsAsList;
    }

    @Override
    public ImmutableList<T> filter(Predicate<T> predicate) {
        return ImmutableList.copyOf( Collections2.filter(itemsAsList, predicate) );
    }
}
