package org.tchw.fakturownia.data.example;

import org.tchw.data.jsontocsv.JsonToCsv;

public class JsonToCsvWriterExample {

    public static void main(String[] args) {
        JsonToCsv.fromFileToSimilarFile("c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt").executeSync();
    }

}
