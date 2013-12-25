package org.tchw.fakturownia.data.example;

import java.io.File;

import org.tchw.generic.data.json.Json;
import org.tchw.generic.data.json.csv.JsonToCsv;
import org.tchw.generic.data.stream.Stream;

public class JsonToCsvWriterExample {

    public static void main(String[] args) {
        String filePath = "c:/Private/Work/Werbum/firma-ksiegarska-werbum.invoices.json.1.txt";
        Stream.fromFile(filePath).passTo(Json.takeFromReader()).passTo(JsonToCsv.takeFromJsonArray()).toFile(new File(filePath + ".csv"));
    }

}
