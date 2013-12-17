package org.tchw.fakturownia.api.example;

import java.util.Map;

import org.tchw.csvBrowsing.CsvBrowsing;
import org.tchw.csvBrowsing.CsvLineAsMapHandler;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;

public class BrowsingInvoices {

    public static void main(String[] args) {
        String filePath = "c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt.csv";

        ImmutableList<Map<String, String>> asListOfMaps = CsvBrowsing.fromFile(filePath).asListOfMaps();
        ImmutableSet<String> keysWithtLeastOneNotNullValue = findKeysWithAtLeastOneNotNullValue(asListOfMaps);
        System.out.println(Joiner.on("\r\n").join(keysWithtLeastOneNotNullValue));
        System.out.println("Size: " + keysWithtLeastOneNotNullValue.size());
        System.out.println("All : " + asListOfMaps.iterator().next().size());

        //--

        CsvBrowsing.fromFile(filePath).onLineAsMap(new CsvLineAsMapHandler() {
            @Override
            public void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap) {
                print(lineAsMap, "id", "client_id", "number");
            }
        });

    }

    private static void print(Map<String, String> map, String...keys) {
        for (String key : keys) {
            String line = key + "                                            ";
            System.out.println(line.substring(0, 25) + ": " + map.get(key));
        }
        System.out.println("--");
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
