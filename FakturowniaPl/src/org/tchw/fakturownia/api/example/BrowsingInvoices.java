package org.tchw.fakturownia.api.example;

import java.util.Map;

import org.tchw.csvBrowsing.CsvBrowsing;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;

public class BrowsingInvoices {

    public static void main(String[] args) {
        ImmutableList<Map<String, String>> asListOfMaps = CsvBrowsing.fromFile("c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt.csv").asListOfMaps();
        ImmutableSet<String> keysWithtLeastOneNotNullValue = findKeysWithAtLeastOneNotNullValue(asListOfMaps);
        System.out.println(Joiner.on("\r\n").join(keysWithtLeastOneNotNullValue));
        System.out.println("Size: " + keysWithtLeastOneNotNullValue.size());
        System.out.println("All : " + asListOfMaps.iterator().next().size());

    }

    private static ImmutableSortedSet<String> findKeysWithAtLeastOneNotNullValue(ImmutableList<Map<String, String>> asListOfMaps) {
        UnmodifiableIterator<Map<String, String>> iterator = asListOfMaps.iterator();
        ImmutableSortedSet.Builder<String> builder = ImmutableSortedSet.naturalOrder();
        while(iterator.hasNext()) {
            Map<String, String> record = iterator.next();
            for (String key : record.keySet()) {
                String value = record.get(key);
                if( notNull(value) ) {
                    builder.add(key);
                }
            }
        }
        ImmutableSortedSet<String> keysWithtLeastOneNotNullValue = builder.build();
        return keysWithtLeastOneNotNullValue;
    }

    private static boolean notNull(String value) {
        return value != null && !"null".equals(value);
    }

}
