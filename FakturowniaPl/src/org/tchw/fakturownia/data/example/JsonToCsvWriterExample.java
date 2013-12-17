package org.tchw.fakturownia.data.example;

import java.io.File;

import org.tchw.data.json.Json;
import org.tchw.data.jsontocsv.JsonToCsv;

public class JsonToCsvWriterExample {

    public static void main(String[] args) {
        String filePath = "c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt";
        Json.fromFile(filePath).passTo(JsonToCsv.takeFromJson())
            .toFile(new File(filePath + ".csv"))
            .executeSync();
    }

}
