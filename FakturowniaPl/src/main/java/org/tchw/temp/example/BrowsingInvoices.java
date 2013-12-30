package org.tchw.temp.example;

import java.util.Map;

import org.tchw.generic.stream.csvBrowsing.CsvBrowsing;
import org.tchw.generic.stream.csvBrowsing.CsvLineAsMapHandler;

import com.google.common.collect.ImmutableSortedMap;

public class BrowsingInvoices {

    public static void main(String[] args) {
        String filePath = "c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt.csv";
        CsvAnalyzer.analyzeCsv(filePath);

        CsvBrowsing.fromFile(filePath).onLineAsMap(new CsvLineAsMapHandler() {
            @Override
            public void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap) {
                print(lineAsMap, "id", "client_id", "number", "price_gross");
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
