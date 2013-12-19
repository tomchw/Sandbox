package org.tchw.fakturownia.api.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class Products {

    private final List<Product> products;

    public Products(JSONArray json) {
        this.products = initProducts(json);
    }

    private List<Product> initProducts(JSONArray json) {
        try {
            Builder<Product> builder = ImmutableList.builder();
            for (int i = 0; i < json.length(); i++) {
                builder.add(new Product(json.getJSONObject(i)));
            }
            return builder.build();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
