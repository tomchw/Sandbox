package org.tchw.fakturownia.data.example;

import java.util.Map;

import org.tchw.data.csvBrowsing.CsvBrowsing;
import org.tchw.data.csvBrowsing.CsvLineAsMapHandler;

import com.google.common.collect.ImmutableSortedMap;

public class BrowsingProducts {

    public static void main(String[] args) {
        String filePath = "c:/Private/Work/Werbum/firma-ksiegarska-werbum.products.json.1.txt.csv";
        CsvAnalyzer.analyzeCsv(filePath);

        CsvBrowsing.fromFile(filePath).onLineAsMap(new CsvLineAsMapHandler() {
            @Override
            public void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap) {
                print(lineAsMap, "id", "code", "name", "purchase_price_gross", "price_gross", "quantity");
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
}
