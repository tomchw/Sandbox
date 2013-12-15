package org.tchw.fakturownia.api.example;

import java.util.Map;

import org.tchw.csvBrowsing.CsvBrowsing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

public class BrowsingInvoices {

    public static void main(String[] args) {
        ImmutableList<Map<String, String>> asListOfMaps = CsvBrowsing.fromFile("c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt.csv").asListOfMaps();
        ImmutableSet<String> keysWithtLeastOneNotNullValue = findKeysWithAtLeastOneNotNullValue(asListOfMaps);
        System.out.println(keysWithtLeastOneNotNullValue);

    }

    private static ImmutableSet<String> findKeysWithAtLeastOneNotNullValue(
            ImmutableList<Map<String, String>> asListOfMaps) {
        UnmodifiableIterator<Map<String, String>> iterator = asListOfMaps.iterator();
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        while(iterator.hasNext()) {
            Map<String, String> record = iterator.next();
            for (String key : record.keySet()) {
                String value = record.get(key);
                if( value != null ) {
                    builder.add(key);
                }
            }
        }
        ImmutableSet<String> keysWithtLeastOneNotNullValue = builder.build();
        return keysWithtLeastOneNotNullValue;
    }

}
