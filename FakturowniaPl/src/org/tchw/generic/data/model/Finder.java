package org.tchw.generic.data.model;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public interface Finder<T> {

    T byId(String id);

    ImmutableList<T> all();

    ImmutableList<T> filter(Predicate<T> predicate);
}
